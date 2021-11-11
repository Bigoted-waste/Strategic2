package com.cola.controller;

import com.cola.mapper.ProjectInformationMapper;
import com.cola.pojo.ProjectInformation;
import com.cola.service.FileService;
import com.cola.service.ProjectInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    FileService fileService;

    /**
     * 查看所有项目数据
     *
     * @return
     */
    @RequestMapping("/allp")
    public String list(Model model) {
        List<ProjectInformation> projectInformationList = projectInformationService.queryAll();
//        System.out.println(projectInformationList);
        model.addAttribute("list", projectInformationList);
        return "allProject";
    }

    @RequestMapping("/delp/{id}")
    public String deleteProject(@PathVariable("id") Integer id) {
        projectInformationService.delProjectInformation(id);
        return "redirect:/allp";
    }

    /**
     * 按照年份合并pdf文件
     *
     * @param year
     * @return
     */
    @RequestMapping("/merge")
    public String mergePdf(@RequestParam("year") String year) {
        String path = "src/main/resources/static/ProjectLibrary/" + year;
        try {
            String savePath = "/home/cola/IdeaProjects/Strategic2/" + year + "年汇总.pdf";
            fileService.mergeDocuments(path, savePath);
        } catch (IOException e) {
            e.printStackTrace();

            //TODO
            System.out.println("合并错误!");
        }
        return "redirect:/allp";
    }
}
