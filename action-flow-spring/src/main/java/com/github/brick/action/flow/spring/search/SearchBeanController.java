package com.github.brick.action.flow.spring.search;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/action_flow/search")
public class SearchBeanController {
    @Autowired
    private SpringBeanSearch search;

    @GetMapping("/")
    public List<SearchResult> beans() {
        return search.searchAll();
    }


}
