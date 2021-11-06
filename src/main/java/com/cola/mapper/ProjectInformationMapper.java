package com.cola.mapper;

import com.cola.pojo.ProjectInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午4:24
 */


@Mapper
@Repository
public interface ProjectInformationMapper {

    //查询所有
    List<ProjectInformation> queryAll();

    //删除
    void delProjectInformation(int id);

    //增加
    void addProjectInformation(ProjectInformation projectInformation);

}
