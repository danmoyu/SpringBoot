package com.omo.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MoYu
 * @create 2021-04-25 14:52
 */
//@Controller
//@ResponseBody

@RestController
public class BootController {

    @RequestMapping("/hello")
    public String testBoot(){

        return "你好";
    }
}
