package com.jlw.controller;

/**
 * @program: reggie_take_out
 * @description: 文件上传下载
 * @author: jlw
 * @create: 2024-07-26 15:47
 **/

import com.jlw.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class ConmmonController {
    /**
    * @Description: 文件上传
    * @Param:
    * @return:
    * @Author: jlw
    * @Date:
    */
    @Value("${reggie.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file ) throws IOException {
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件夹名
        String s= UUID.randomUUID().toString();
        String fileName=s+suffix;
        file.transferTo(new File(basePath+fileName));
        return R.success(fileName);
    }
    /**
     * @Description: 文件下载
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @GetMapping("/download")
    public void Download(String name, HttpServletResponse response){
        //输入流，通过输入流读取文件内容
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("/image/jpeg");
            int len=0;
            byte[] bytes=new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
                outputStream.close();
                fileInputStream.close();
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
