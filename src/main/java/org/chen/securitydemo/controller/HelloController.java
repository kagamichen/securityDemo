package org.chen.securitydemo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("user/hello")
    public String user(){
        return "user";
    }
    @GetMapping("db/hello")
    public String db(){
        return "db";
    }
    @GetMapping("admin/hello")
    public String admin(){
        return "admin";
    }



}
