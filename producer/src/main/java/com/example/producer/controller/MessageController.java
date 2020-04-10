package com.example.producer.controller;

import com.example.api.domain.EurakeClientFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MessageController implements EurakeClientFeignService {
    @Value("${server.port}")
    String port;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 供consumer调用
     * @param name
     * @return
     */
    public String getMessage(@RequestParam("name")String name){
        return "Hi " + name + " ,I am from port:" + port;
    }

    /**
     * 调用consumer
     * @param name
     * @return
     */
    @GetMapping("/callConsumer")
    public String callConsumer(@RequestParam("name")String name){
        return restTemplate.getForObject("http://consumer/calledByProvider?name=" + name, String.class);
    }
}
