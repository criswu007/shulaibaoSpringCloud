package com.example.consumer.feignClient;

import com.example.consumer.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * spring.application.name=producer 的服务接口列表
 */
@FeignClient(value = "producer", configuration = FeignConfig.class)
public interface EurakeClientFeignService {
    @GetMapping("/get")
    String producerGet(@RequestParam("name")String name);
}
