package com.example.commondatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//去除数据源自动配置
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CommonDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonDatasourceApplication.class, args);
    }

}
