package com.example.api.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Description: Feign配置类
 *
 * @author use
 * Modification History:
 * Date             Author      Version     Description
 * ------------------------------------------------------------------
 * 2020-4-9 21:28  use      1.0        1.0 Version
 */
@Configuration
public class FeignConfig {

    /**
     * feign远程调用失败后，进行重试
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 5);
    }
}
