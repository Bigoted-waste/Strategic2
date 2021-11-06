package com.cola;

import com.cola.mapper.ProjectInformationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class Strategic2ApplicationTests {

    @Autowired
    ProjectInformationMapper projectInformationMapper;

    @Test
    void contextLoads() {
        System.out.println(projectInformationMapper.queryAll());
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");//设置日期格式

    @Test
    void timeTest() {
        Date date = new Date();
        String time = sdf.format(date);
        System.out.println(time);
        System.out.println(time.equals("2021"));
    }

    @Test
    void doubleTest() {
//        四舍五入
        double f = 111231.5585;
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }

    @Test
    void mkdirTest(){
        File file=new File("ProjectLibrary/aaa");
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
    }

}
