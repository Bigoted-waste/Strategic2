package com.cola.service;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class WordToPDF {
    /**
     * 将文件word文件存储为pdf（存储在word路径下）
     * @param filePath  文件路径
     * @param fileName  文件名称
     * @return
     * @throws Exception
     */
    public String convert(String filePath, String fileName) throws Exception {   //, String urlRoot
        setLicense();
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String pdfPath = String.format("%s/%s.pdf", filePath, name);
        String wordPath = String.format("%s/%s", filePath, fileName);

        File file = new File(pdfPath);
        FileOutputStream stream = new FileOutputStream(file);

        Document doc = new Document(wordPath);
        doc.save(stream, SaveFormat.PDF);
        stream.close();

//        String url = String.format("%s\\%s", urlRoot, getRelativePath(pdfPath));
        return "Helper.normalizeUrl(url);";
    }

    /**
     * 获取相对路径
     * @param path
     * @return
     */
    private static String getRelativePath(String path) {
        String truncatePath = path.substring(path.lastIndexOf("uploads"));
        return truncatePath.substring(truncatePath.indexOf("\\"));
    }

    /**
     * 获取许可证
     * @throws Exception
     */
    private static void setLicense() throws Exception {
        InputStream stream = WordToPDF.class.getClassLoader().getResourceAsStream("license.xml");
        License license = new License();
        license.setLicense(stream);
    }

}

