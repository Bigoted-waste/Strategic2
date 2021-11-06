package com.cola.controller;

import com.cola.mapper.ProjectInformationMapper;
import com.cola.pojo.ProjectInformation;
import com.cola.service.ProjectInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/5 下午9:07
 */

@Controller
public class AllProjectController {


    @Autowired
    ProjectInformationService projectInformationService;

    /**
     * 查看所有项目数据
     *
     * @return
     */
    @RequestMapping("/allp")
    public String list(Model model) {
        List<ProjectInformation> projectInformationList = projectInformationService.queryAll();
        System.out.println(projectInformationList);
        model.addAttribute("list", projectInformationList);
        return "allProject";
    }

    @RequestMapping("/delp/{id}")
    public String deleteProject(@PathVariable("id") Integer id) {
        projectInformationService.delProjectInformation(id);
        return "redirect:/allp";
    }
}
