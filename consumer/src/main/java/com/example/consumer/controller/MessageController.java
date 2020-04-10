package com.example.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MessageController {

    @Value("${server.port}")
    String port;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 调用provider
     * @param name
     * @return
     */
    @GetMapping("/show")
    public String showMessage(@RequestParam String name){
        return restTemplate.getForObject("http://producer/get?name="+name, String.class);
    }

    /**
     * 供provider调用
     * @param name
     * @return
     */
    @GetMapping("/calledByProvider")
    public String calledByProvider(@RequestParam String name){
        return name + "from  " + port;
    }
}
