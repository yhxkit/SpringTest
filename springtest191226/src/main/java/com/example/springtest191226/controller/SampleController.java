package com.example.springtest191226.controller;

import com.example.springtest191226.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("/hello")
    public String hello(){
        return strTest("hello");
    }

    @GetMapping("/getSample")
    public String hi(){
        return sampleService.getSample();
    }


    private String strTest(String arg){
        return arg+"!!!";
    }

}
