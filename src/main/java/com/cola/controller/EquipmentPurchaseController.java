package com.cola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午11:08
 */


@Controller
public class EquipmentPurchaseController {//设备明细表

    @GetMapping("/gep/{pName}")
    public String GoEquipmentPurchase(@PathVariable("pName")String pName, Model model){
        String Path="/zlb/ProjectLibrary/"+pName+"/中央对地方专项转移区域绩效目标表.pdf";
        model.addAttribute("pname",Path);
        return "template_show.html";
    }

}
