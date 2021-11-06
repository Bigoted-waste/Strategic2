package com.cola.controller;

import com.cola.pojo.ProjectInformation;
import com.cola.pojo.ProjectType;
import com.cola.service.ProjectInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/5 下午8:00
 */

@Controller
public class LoginController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");//设置日期格式


    @Autowired
    ProjectInformationService projectInformationService;

    @RequestMapping("/user/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {

        //具体业务:
        if ("admin".equals(username) || "123".equals(password)) {
            session.setAttribute("loginUser", username);
            return "redirect:/sm";
//            return "redirect:/main.html";
        } else {
            //告诉用户,你登录失败了
            model.addAttribute("msg", "用户名或密码错误！");
            return "index";
        }
    }

    //默认搜索2021年份
    @RequestMapping("/sm")
    public String showMessage(Model model) {
        List<ProjectInformation> projectInformationList = projectInformationService.queryAll();
        List<ProjectType> projectTypes = queryAllProjectType(projectInformationList);
        List<ProjectType> yearProjectTypes = queryProjectTypeByYear(projectInformationList, 2021, 2021);

        double allCapital = 0;
        int allProjectNumber = 0;

        String year = sdf.format(new Date());
        double capital2021 = 0;
        int projectNumber2021 = 0;

        for (ProjectInformation projectInformation : projectInformationList) {
            //计算总经费
            double projectCapital = projectInformation.getCenter_fund() + projectInformation.getSchool_fund() + projectInformation.getLocal_fund();
            allCapital += projectCapital;

            allProjectNumber++;
            Date creation_time = projectInformation.getCreation_time();
            String time = sdf.format(creation_time);

            if (time.equals(year)) {
                capital2021 += projectCapital;
                projectNumber2021++;
            }
        }

        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("yearProjectTypes", yearProjectTypes);
        model.addAttribute("allCapital", doubleTwo(allCapital));
        model.addAttribute("allProjectNumber", allProjectNumber);
        model.addAttribute("capital2021", doubleTwo(capital2021));
        model.addAttribute("year", year);
        model.addAttribute("projectNumber2021", projectNumber2021);
        return "dashboard";
    }

    //按年份索引
    /*
        [2020-2019] 包含
     */
    @RequestMapping("/qsm")
    public String queryMessage(@RequestParam("startYear") String startYear, @RequestParam("endYear") String endYear, Model model) {
        System.out.println("------------------->");
        List<ProjectInformation> projectInformationList = projectInformationService.queryAll();
        List<ProjectType> projectTypes = queryAllProjectType(projectInformationList);
        List<ProjectType> yearProjectTypes = queryProjectTypeByYear(projectInformationList, Integer.parseInt(startYear), Integer.parseInt(endYear));

        double allCapital = 0;
        int allProjectNumber = 0;

        double capital = 0;
        int projectNumber = 0;
        String year;
        if (startYear.equals(endYear)) {
            year = startYear + "";
        } else {
            year = startYear + "-" + endYear;
        }
        for (ProjectInformation projectInformation : projectInformationList) {
            //计算总经费
            double projectCapital = projectInformation.getCenter_fund() + projectInformation.getSchool_fund() + projectInformation.getLocal_fund();
            allCapital += projectCapital;

            allProjectNumber++;
            Date creation_time = projectInformation.getCreation_time();
            String time = sdf.format(creation_time);

            int i = Integer.parseInt(time);

            //判断是否包含 startYear--endYear
            if (i >= (Integer.parseInt(startYear)) && i <= (Integer.parseInt(endYear))) {
                capital += projectCapital;
                projectNumber++;
            }
        }

        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("yearProjectTypes", yearProjectTypes);
        model.addAttribute("allCapital", doubleTwo(allCapital));
        model.addAttribute("allProjectNumber", allProjectNumber);
        model.addAttribute("capital2021", doubleTwo(capital));
        model.addAttribute("year", year);
        model.addAttribute("projectNumber2021", projectNumber);
        return "dashboard";
    }


    /**
     * 保留两位小数
     *
     * @param number
     */
    public double doubleTwo(double number) {
        //        四舍五入
        BigDecimal b = new BigDecimal(number);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    /**
     * 统计所有项目类型
     * @param p
     * @return
     */
    private List<ProjectType> queryAllProjectType(List<ProjectInformation> p) {
        List<ProjectType> projectTypes = new ArrayList<>();
        projectTypes.add(new ProjectType(0, "教研实验平台"));
        projectTypes.add(new ProjectType(0, "科研平台"));
        projectTypes.add(new ProjectType(0, "时间基地"));
        projectTypes.add(new ProjectType(0, "公共服务体系"));
        projectTypes.add(new ProjectType(0, "人才队伍建设"));
        for (ProjectInformation projectInformation : p) {
            switch (projectInformation.getProject_type()) {
                case 1:
                    projectTypes.get(1).setValue(projectTypes.get(1).getValue() + 1);
                    break;
                case 2:
                    projectTypes.get(2).setValue(projectTypes.get(2).getValue() + 1);
                    break;
                case 3:
                    projectTypes.get(3).setValue(projectTypes.get(3).getValue() + 1);
                    break;
                case 4:
                    projectTypes.get(4).setValue(projectTypes.get(4).getValue() + 1);
                    break;
                case 5:
                    projectTypes.get(5).setValue(projectTypes.get(5).getValue() + 1);
                    break;
            }
        }
        return projectTypes;
    }

    /**
     * 按找年份统计项目类型
     *  如果要查找一年，startYear 和 endYear 相同即可
     * @param p
     * @param startYear
     * @param endYear
     * @return
     */
    public List<ProjectType> queryProjectTypeByYear(List<ProjectInformation> p, int startYear, int endYear) {
        List<ProjectType> projectTypes = new ArrayList<>();
        projectTypes.add(new ProjectType(0, "教研实验平台"));
        projectTypes.add(new ProjectType(0, "科研平台"));
        projectTypes.add(new ProjectType(0, "时间基地"));
        projectTypes.add(new ProjectType(0, "公共服务体系"));
        projectTypes.add(new ProjectType(0, "人才队伍建设"));
        for (ProjectInformation projectInformation : p) {
            Date creation_time = projectInformation.getCreation_time();
            int time = Integer.parseInt(sdf.format(creation_time));

            if (time >= startYear && time <= endYear) {
                switch (projectInformation.getProject_type()) {
                    case 1:
                        projectTypes.get(1).setValue(projectTypes.get(1).getValue() + 1);
                        break;
                    case 2:
                        projectTypes.get(2).setValue(projectTypes.get(2).getValue() + 1);
                        break;
                    case 3:
                        projectTypes.get(3).setValue(projectTypes.get(3).getValue() + 1);
                        break;
                    case 4:
                        projectTypes.get(4).setValue(projectTypes.get(4).getValue() + 1);
                        break;
                    case 5:
                        projectTypes.get(5).setValue(projectTypes.get(5).getValue() + 1);
                        break;
                }
            }
        }
        return projectTypes;
    }


}
