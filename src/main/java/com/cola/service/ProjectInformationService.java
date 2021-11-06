package com.cola.service;

import com.cola.mapper.ProjectInformationMapper;
import com.cola.pojo.ProjectInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午11:16
 */


@Service
public class ProjectInformationService {

    @Autowired
    ProjectInformationMapper projectInformationMapper;

    public List<ProjectInformation> queryAll(){
        List<ProjectInformation> projectInformations = projectInformationMapper.queryAll();
        return projectInformations;
    }

    public void delProjectInformation(int id){
        projectInformationMapper.delProjectInformation(id);
    }

    public void addProjectInformation(ProjectInformation projectInformation){
        projectInformationMapper.addProjectInformation(projectInformation);
    }
}
