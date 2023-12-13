package com.EShop.EShop.service.impl;

import com.EShop.EShop.service.FileUpload;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Service
public class FileUploadImpl implements FileUpload {

    private final Cloudinary cloudinary;

    public FileUploadImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return  cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}
