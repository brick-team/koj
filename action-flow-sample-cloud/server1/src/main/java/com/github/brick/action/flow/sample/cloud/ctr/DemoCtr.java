package com.github.brick.action.flow.sample.cloud.ctr;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoCtr {
    @GetMapping("/d")
    public String d() {
        return "d";
    }
}
