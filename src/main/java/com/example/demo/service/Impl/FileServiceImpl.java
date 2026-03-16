package com.example.demo.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private final Cloudinary cloudinary;

    public FileServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), 
                ObjectUtils.asMap("resource_type", "auto"));
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Cloudinary upload failed", e);
        }
    }

    // ADD THIS METHOD TO FIX THE ERROR
    @Override
    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Deleted from Cloudinary: " + publicId);
        } catch (IOException e) {
            throw new RuntimeException("Cloudinary delete failed", e);
        }
    }
}