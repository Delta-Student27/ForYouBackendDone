package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Import(TestSecurityConfig.class)
// @ActiveProfiles("test")
@AutoConfigureMockMvc
class DemoApplicationTests {


    @Test
    void contextLoads() {
    }

}
