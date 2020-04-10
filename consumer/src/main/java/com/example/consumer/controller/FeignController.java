package com.example.consumer.controller;

import com.example.consumer.feignClient.EurakeClientFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: Feign方式调用服务者
 *
 * @author wudb
 * Modification History:
 * Date             Author      Version     Description
 * ------------------------------------------------------------------
 * 2020-4-9 21:23  use      1.0        1.0 Version
 */
@RestController
public class FeignController {
    @Autowired
    private EurakeClientFeignService eurakeClientFeignService;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/feign")
    public String feign(@RequestParam("name") String name) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("producer");
        System.out.println(serviceInstance.getHost() + ":" + serviceInstance.getPort());
        return eurakeClientFeignService.producerGet(name);
    }
}
