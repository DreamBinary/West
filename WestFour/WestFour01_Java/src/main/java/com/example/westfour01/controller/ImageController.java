package com.example.westfour01.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author CXQ
 * @date 2022/2/25
 */

@RestController
@RequestMapping("/image")
public class ImageController {
    @PostMapping("/upload")
    public void upload(@RequestParam("uploaded_file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                //存放照片
                String path = "D://MyJaVa//westfourfile//" + file.getOriginalFilename();
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(path));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response, @RequestParam("username") String username) {
        String path = "D://MyJaVa//westfourfile//" + username + ".jpg";
        File file = new File(path);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(file);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
