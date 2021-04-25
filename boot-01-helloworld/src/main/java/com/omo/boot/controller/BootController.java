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

        System.out.println("我是分支创建的");

        System.out.println("我在21行输出一句话");

        System.out.println("分支我在23行添加一句话");
        return "你好+helloWord";
    }
}
