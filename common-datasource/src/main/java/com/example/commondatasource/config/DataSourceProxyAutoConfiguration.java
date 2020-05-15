package com.example.commondatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 分布式事务seata的代理类
 *
 * @author use
 * Modification History:
 * Date             Author      Version     Description
 * ------------------------------------------------------------------
 * 2020-4-10 15:35  use      1.0        1.0 Version
 */
@Configuration
public class DataSourceProxyAutoConfiguration {

    private DataSourceProperties dataSourceProperties;
    public DataSourceProxyAutoConfiguration() {
        super();
    }

    public DataSourceProxyAutoConfiguration(DataSourceProperties dataSourceProperties) {

    }

    /**
     * 全局的配置信息
     *
     * @return
     */
    @Bean
    public GlobalConfig mpGlobalConfig() {
        // 全局配置文件
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        //IdType.AUTO  主键自增 (默认不设置，需要数据库设置主键自增)
        //IdType.INPUT 自定义
        dbConfig.setIdType(IdType.INPUT);
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;
    }

    @Primary
    @Bean("masterDataSource")
    public DataSource getDataSource(@Qualifier("dbConfig") DataSourceConfig config) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setDriverClassName(config.getDriverClassName());

        //具体配置
        dataSource.setInitialSize(config.getInitialSize());
        dataSource.setMinIdle(config.getMinIdle());
        dataSource.setMaxActive(config.getMaxActive());
        dataSource.setMaxWait(config.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(config.getValidationQuery());
        dataSource.setTestWhileIdle(config.isTestWhileIdle());
        dataSource.setTestOnBorrow(config.isTestOnBorrow());
        dataSource.setTestOnReturn(config.isTestOnReturn());
        //返回代理数据源，使用seata实现分布式事务
        return dataSource;
//        return new DataSourceProxy(dataSource);
    }

    @Bean("proxyMasterDataSource")
    public DataSourceProxy getProxyDataSource(@Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    /**
     * sessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public MybatisSqlSessionFactoryBean getSqlSessionFactoryBean(@Qualifier("masterDataSource") DataSource dataSource, GlobalConfig globalConfig) {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setGlobalConfig(globalConfig);
        configuration.setMapUnderscoreToCamelCase(true);    //下划线转驼峰
        sqlSessionFactoryBean.setConfiguration(configuration);

        List<Interceptor> interceptors = new ArrayList<>();
        //设置分页插件
        interceptors.add(new PaginationInterceptor());
        //设置分表插件
//        interceptors.add(new TableShardInterceptor());

        sqlSessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[interceptors.size()]));

        //注册EnumTypeHandler
//        sqlSessionFactoryBean.setTypeHandlersPackage(IMP);
        return sqlSessionFactoryBean;
    }

//
//    /**
//     * 包扫描
//     * @return
//     */
//    @Bean
//    public MapperScannerConfigurer masterMapperScannerConfigurer() {
//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        configurer.setBasePackage("com.example.commondatasource.mapper");
//        //设置上面的factory name
//        configurer.setSqlSessionFactoryBeanName("masterSqlSessionFactory");
//        return configurer;
//    }


    /**
     * 事务管理器
     * @return
     */
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }
}
