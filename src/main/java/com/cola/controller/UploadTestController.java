package com.cola.controller;

import com.cola.service.FileService;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author cola
 * @version 1.0
 * @date 2021/10/11 下午1:30
 */

@Controller
public class UploadTestController { //上传页面

    @Autowired
    FileService fileService;

    @RequestMapping("/ul")
    public String goUpload() {
        return "testUpload";
    }

    @PostMapping("/upload")
    public String uploadTest(@RequestParam("file") MultipartFile[] files,String pname, RedirectAttributes redirectAttributes) {
        String fileAllName = "";
        //创建文件夹
        fileService.mkdirFileProject(pname);
        for (MultipartFile file : files) {
            fileAllName += file.getOriginalFilename();
            System.out.println(file.getOriginalFilename());
            try {
                fileService.uploadFileToLibrary(file,pname);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (OpenXML4JException e) {
                e.printStackTrace();
            } catch (XmlException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + fileAllName + "!");
        return "redirect:/ul";
    }
}
