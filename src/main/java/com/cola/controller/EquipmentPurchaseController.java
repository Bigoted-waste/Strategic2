package com.cola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午11:08
 */


@Controller
public class EquipmentPurchaseController {

    @GetMapping("/gep/{pName}") //设备明细表
    public String GoEquipmentPurchase(@PathVariable("pName")String pName){

        return "/"+pName+"/设备购置明细表.html";
    }
}
