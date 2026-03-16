package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * Uploads a file to Cloudinary and returns the secure URL.
     * @param file The multipart file from the controller
     * @return Secure URL string from Cloudinary
     */
    String uploadFile(MultipartFile file);
    
    /**
     * Optional: Deletes a file from Cloudinary using its Public ID.
     * @param publicId The unique ID Cloudinary assigned to the image
     */
    void deleteFile(String publicId);
}