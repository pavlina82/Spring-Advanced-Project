package com.EShop.EShop.controller;


import com.EShop.EShop.service.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping
public class FileUploadController {
    private final FileUpload fileUpload;

    public FileUploadController(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    @RequestMapping("/")
    public String home(){
        return "home";
    }
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile,
                             Model model) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);
        model.addAttribute("imageURL",imageURL);
        return "gallery";
    }
}
