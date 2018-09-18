package com.nika.chat;

import com.nika.chat.client.SimpleChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ChatClientApplication {

    public static void main(String[] args) throws Exception {
//        new SimpleChatClient().run();
        SpringApplication.run(ChatClientApplication.class, args);
    }
}
