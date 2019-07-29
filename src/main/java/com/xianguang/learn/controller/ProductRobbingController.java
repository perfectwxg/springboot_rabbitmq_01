package com.xianguang.learn.controller;

import com.xianguang.learn.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kongchengguying
 */
@RestController
@RequestMapping("/product")
public class ProductRobbingController {
    @Autowired
    private InitService initService;

    @RequestMapping("/robbing")
    public void robbing(){
        //TODO:初始化请求数量
        initService.generateMultiThread();
    }
}
