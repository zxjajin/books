package com.ajin.book.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author ajin
 * @create 2023-10-14 19:15
 */
public class FileUtils {


    public static String uploadFile(MultipartFile[] files) throws IOException {
        // 1. 用数组MultipartFile[]来表示多文件,所以遍历数组,对其中的文件进行逐一操作
        for (MultipartFile file : files) {
            // 2. 通过一顿file.getXXX()的操作,获取文件信息。
            // 2.1 这里用文件名举个栗子
            String filename = file.getOriginalFilename();
            // 3. 接下来调用方法来保存文件到本地磁盘, 返回的是保存后的文件路径
            String filePath = savaFileByNio((FileInputStream) file.getInputStream(), filename);
            // 4. 保存文件信息到数据库
            // 4.1 搞个实体类，把你需要的文件信息保存到实体类中
            // 4.2 调用Service层或者Dao层，保存数据库即可。
            return "/static/"+filename;
        }
        return null;
    }

    public static String savaFileByNio(FileInputStream fis, String fileName) {
        // 这个路径最后是在: 你的项目路径/FileSpace  也就是和src同级
        String fileSpace = System.getProperty("user.dir") + File.separator + "static";
//        String fileSpace = "/opt" + File.separator + "static";
//        String fileSpace = "F:\\daima\\books\\static";
        String path = fileSpace + File.separator + fileName;
        // 判断父文件夹是否存在
        File file = new File(path);
        if (file.getParentFile() != null || !file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
        // 通过NIO保存文件到本地磁盘
        try {
            FileOutputStream fos = new FileOutputStream(path);
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
