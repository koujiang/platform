package com.unknown.start;

import com.unknown.jms.JmsProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.unknown.**")
public class StartApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
