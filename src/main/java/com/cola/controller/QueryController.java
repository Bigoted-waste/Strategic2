package com.cola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/5 下午9:27
 */

@Controller
public class QueryController {

    /**
     * 查询
     */
    @RequestMapping("/query")
    public String queryProject(){
        System.out.println("====>去查询页面");
        return "query";
    }
}
