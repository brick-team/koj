package com.github.brick.action.flow.sample.cloud.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoCtr {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/dd")
    public String dd() {
        String forObject = restTemplate.getForObject("http://server1/d", String.class);
        return "dd";
    }
}
