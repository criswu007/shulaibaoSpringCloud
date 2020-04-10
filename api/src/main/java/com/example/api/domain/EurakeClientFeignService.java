package com.example.api.domain;

import com.example.api.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring.application.name=producer 的服务接口列表
 */
@FeignClient(value = "producer", configuration = FeignConfig.class)
@RestController
public interface EurakeClientFeignService {
    @GetMapping("/get")
    String getMessage(@RequestParam("name") String name);
}
