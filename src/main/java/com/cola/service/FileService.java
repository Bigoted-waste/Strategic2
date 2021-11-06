package com.cola.service;

import com.cola.pojo.ProjectInformation;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/11 下午4:05
 */


@Service
public class FileService {

    @Autowired
    ProjectInformationService projectInformationService;

    private final String ALL_PROJECT_PATH = "ProjectLibrary/";

    /**
     * 按照项目名称建立项目库
     *
     * @param projectName   项目名称
     * @return
     */
    public boolean mkdirFileProject(String projectName) {
        File file = new File(ALL_PROJECT_PATH + projectName);
        if (!file.exists()) {//如果文件夹不存在
            file.mkdir();//创建文件夹
            return true;
        } else
            return false;
    }

    /**
     * 将文件存储到文件库中
     * @param file
     * @param projectName
     * @throws Exception
     */
    public void uploadFileToLibrary(MultipartFile file, String projectName) throws Exception {
        String filePath = ALL_PROJECT_PATH + projectName + "/";
        WordToPDF wordToPDF = new WordToPDF();
        if (file.getOriginalFilename().indexOf("申请书") != -1) {
            //1、将文件落本地库中
            String path = toFile(file, filePath, "项目申请书");
            if(file.getName().endsWith(".doc")) {
                wordToPDF.convert(filePath, "项目申请书.doc");
            }else {
                wordToPDF.convert(filePath, "项目申请书.docx");
            }
            System.out.println(path);
            //2、解析文件库表中的数据
            //将数据转变成一行一行的数据
            List<String> strings = readWord(path);
            //将数据解析成map
            Map<String, String> projectMap = analysisWord(strings);
            //TODO 落库
//
            DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
            Date date = null;
            date = fmt.parse(projectMap.get("time"));
            ProjectInformation projectInformation = new ProjectInformation(
                    projectMap.get("name"),
                    date,
                    Integer.parseInt(projectMap.get("type")),
                    Double.parseDouble(projectMap.get("CenterFund")),
                    0, 0
            );
//            System.out.println(projectInformation);
//            System.out.println(projectInformation.getCreation_time());
            // 保存到数据库中
            projectInformationService.addProjectInformation(projectInformation);
        } else if (file.getOriginalFilename().indexOf("绩效目标表") != -1) {
            toFile(file, filePath, "中央对地方专项转移区域绩效目标表");
            if (file.getName().endsWith(".doc")) {
                wordToPDF.convert(filePath, "中央对地方专项转移区域绩效目标表.doc");
            }else {
                wordToPDF.convert(filePath, "中央对地方专项转移区域绩效目标表.docx");
            }
        } else if (file.getOriginalFilename().indexOf("购置明细表") != -1) {
            toFile(file, filePath, "支持地方高校改革发展资金设备购置明细表");
            if(file.getName().endsWith(".doc")) {
                wordToPDF.convert(filePath, "支持地方高校改革发展资金设备购置明细表.doc");
            }else {
                wordToPDF.convert(filePath, "支持地方高校改革发展资金设备购置明细表.docx");
            }
        }
    }


    /**
     * 将申请表中的数据解析成Map
     *
     * @param stringList name：项目名称
     *                   time：申报日期
     *                   type：项目类型
     *                   CenterFund：中央财政
     * @return
     */
    public Map<String, String> analysisWord(List<String> stringList) {
        Map<String, String> projectHash = new HashMap<>();
        for (String s : stringList) {
            if (s.indexOf("项  目  名  称") != -1) {
//                System.out.println(s.indexOf('：'));
                String str = s.substring(s.indexOf('：') + 1);
                projectHash.put("name", str);
            } else if (s.indexOf("申  报  日  期") != -1) {
//                System.out.println(s.indexOf('：'));
                String r = s.substring(s.indexOf('：') + 1);
                //将所有空格替换出去
                String str = r.replaceAll(" ", "");
                //2021年6月16日
                projectHash.put("time", str);
            } else if (s.indexOf("R") != -1) {
                String r = s.substring(0, s.indexOf('R'));
                String str = r.replaceAll(" ", "");
                if (str.indexOf("教学实验平台") != -1) {
                    projectHash.put("type", "1");
                } else if (str.indexOf("科研平台") != -1) {
                    projectHash.put("type", "2");
                } else if (str.indexOf("实践基地") != -1) {
                    projectHash.put("type", "3");
                } else if (str.indexOf("公共服务体系") != -1) {
                    projectHash.put("type", "4");
                } else if (str.indexOf("人才队伍建设") != -1) {
                    projectHash.put("type", "5");
                }
            } else if (s.indexOf("其中：中央财政") != -1) {
                String substring = s.substring(s.length() - 6, s.length());
                String str = substring.replaceAll(" ", "");
                projectHash.put("CenterFund", str);
            }
        }
        return projectHash;
    }

    /**
     * 将multipartFile保存到文件库中
     *
     * @param multipartFile 从前台输入的文件
     * @param filePath      保存的文件路径
     * @param fileName      保存的文件名称
     */
    public String toFile(MultipartFile multipartFile, String filePath, String fileName) {
        InputStream in = null;
        OutputStream os = null;
        int n;
        String temporaryFileName = "";
//        File f = new File(multipartFile.getOriginalFilename());
        try {
            in = multipartFile.getInputStream();
            // 得到文件流。以文件流的方式输出到新文件
            // 可以使用byte[] ss = multipartFile.getBytes();代替while
            if (multipartFile.getName().endsWith(".doc")) {
                temporaryFileName = filePath + fileName + ".doc";
            } else {
                temporaryFileName = filePath + fileName + ".docx";
            }
            os = new FileOutputStream(new File(temporaryFileName));
            byte[] buffer = new byte[4096];
            while ((n = in.read(buffer, 0, 4096)) != -1) {
                os.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 输出文件的绝对路径
        return temporaryFileName;
    }

    /**
     * 将文件解析成list字符串
     *
     * @param filePath  //文件路径
     * @return
     */
    public List<String> readWord(String filePath) throws IOException, OpenXML4JException, XmlException {
        List<String> linList = new ArrayList<>();
        String buffer = "";
        if (filePath.endsWith(".doc")) {
            InputStream is = new FileInputStream(new File(filePath));
            WordExtractor ex = new WordExtractor(is);
            buffer = ex.getText();
            ex.close();

            if (buffer.length() > 0) {
                //使用回车换行符分割字符串
                String[] arry = buffer.split("\\n"); // \\r\\n
                for (String string : arry) {
                    linList.add(string.trim());
                }
            }
        } else if (filePath.endsWith(".docx")) {
            OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            buffer = extractor.getText();
            extractor.close();

            if (buffer.length() > 0) {
                //使用换行符分割字符串
                String[] arry = buffer.split("\\n");
                for (String string : arry) {
                    linList.add(string.trim());
                }
            }
        } else {
            return null;
        }
        return linList;
    }


}
