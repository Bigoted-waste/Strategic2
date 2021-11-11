package com.cola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午11:07
 */
@Controller
public class PerformanceObjectivesController {  //项目绩效表

    @GetMapping("/gpo/{pName}/{pYear}")
    public String GoPerformanceObjectives(@PathVariable("pName") String pName, @PathVariable("pYear") Date year, Model model){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String format = dateFormat.format(year);
        String Path="/zlb/ProjectLibrary/"+format+"/"+pName+"/中央对地方专项转移区域绩效目标表.pdf";
        model.addAttribute("pname",Path);
        return "template_show";
    }
}
