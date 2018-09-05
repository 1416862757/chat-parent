package com.nika.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ChatClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatClientApplication.class, args);
    }
}
