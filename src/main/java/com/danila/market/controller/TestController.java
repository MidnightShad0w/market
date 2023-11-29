package com.danila.market.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public List<String> test(){
        List<String> testList = new ArrayList<>();
        testList.add("test");
        testList.add("test2");
        testList.add("test3");
        return testList;
    }
}
