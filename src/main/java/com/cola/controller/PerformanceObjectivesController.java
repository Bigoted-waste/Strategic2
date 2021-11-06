package com.cola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午11:07
 */
@Controller
public class PerformanceObjectivesController {  //项目绩效表

    @GetMapping("/gpo/{pName}")
    public String GoPerformanceObjectives(@PathVariable("pName")String pName){
        return "/"+pName+"/绩效目标表.html";
    }
}
