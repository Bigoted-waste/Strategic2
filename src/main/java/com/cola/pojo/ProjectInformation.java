package com.cola.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/9 下午10:41
 */
@Data
@NoArgsConstructor
public class ProjectInformation {
    /**
     * 项目类型：
     * 教学实验平台   1
     * 科研平台      2
     * 实践基地      3
     * 公共服务体系   4
     * 人才队伍建设   5
     */
    private int id; //项目编号
    private String project_name;    //项目名称
    private Date creation_time;     //建立时间
    private int project_type;       //项目类型
    private double center_fund;     //中央财政
    private double school_fund;     //学校财政
    private double local_fund;      //地方财政

    public ProjectInformation(String project_name, Date creation_time, int project_type, double center_fund, double school_fund, double local_fund) {
        this.project_name = project_name;
        this.creation_time = creation_time;
        this.project_type = project_type;
        this.center_fund = center_fund;
        this.school_fund = school_fund;
        this.local_fund = local_fund;
    }
}
