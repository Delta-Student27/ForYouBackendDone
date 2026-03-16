package com.example.demo.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        
        // Using your provided credentials
        config.put("cloud_name", "dgd3hkrcc"); 
        config.put("api_key", "444421678158631");       
        config.put("api_secret", "ZF_vHqLVsoqrIYVJ4Bwdij_bCuw"); 
        
        return new Cloudinary(config);
    }
}