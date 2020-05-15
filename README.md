# shulaibaoSpringCloud

* 项目结构：
    * register-server:
        ```
        使用Eurake作为注册中心，consumer、producer分别作为消费者、生产者
        ```
    * api:
        ```
        1. 将producer的服务列表抽离，作为单独模块，使用Feign进行服务调用
        
        2. @FeignClient(value = "producer")注解每个接口，value对应每个producer的spring.application.name
        
        3. 供consumer、producer引用，producer集成client接口即可，consumer自动装配注入接口实例即可
        ```
    * common-datasource:
        ```
        1. 数据源抽离，作为单独模块，使用mybatisPlus作为ORM框架
        
        2. 每个需要使用数据源的producer，需要引入此模块并配置数据源信息
        
        3. 分布式事务使用alibaba.seata，注意：producer需要配置register.conf、file.conf
        ```
    * consumer:
        ```
        1. 消费者，服务注册到eurake
        
        2. 因使用feign进行服务调用，启动类需要添加@EnableFeignClients
        
        3. 引入api模块，自动装配接口实例，即可调用
        ```
    * producer:
        ```
        1. 生产者，服务注册到eurake
        
        2. 引入api模块，继承其接口
        
        3. 引入common-datasource模块，并配置数据源信息和seata的配置文件
        ```
    * nacos-consumer:
        ```
        1. 消费者，服务注册到nacos
                    
        2. 因使用feign进行服务调用，启动类需要添加@EnableFeignClients
                    
        3. 引入api模块，自动装配接口实例，即可调用
        ```
    * nacos-producer:
        ```
        1. 生产者，服务注册到nacos
        
        2. 引入api模块，继承其接口
        
        3. 引入common-datasource模块，并配置数据源信息和seata的配置文件
        ```
       
* 版本控制：
    
    | SpringBoot | 2.1.0.RELEASE |
    | :----: | :----: |
    | SpringCloud | GreenWich.RELEASE |
    | spring-cloud-alibaba-dependencies(nacos,seata) | 0.9.0 |
    | com.alibaba.cloud(seata) | 2.1.0.RELEASE |
        
* 参考博客：   https://blog.csdn.net/chengyuqiang/article/details/90645498