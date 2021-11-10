package com.cola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午11:05
 */


@Controller
public class ProjectApplicationController { //项目申请表

    @GetMapping("/gpa/{pName}")
    public String GoProjectApplication(@PathVariable("pName")String pName, Model model){
        String Path="/zlb/ProjectLibrary/"+pName+"/项目申请书.pdf";
        model.addAttribute("pname",Path);
        return "template_show";
    }
}
