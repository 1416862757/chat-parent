package com.nika.chat.controller;

import com.nika.chat.client.SimpleChatClient;
import com.nika.chat.common.entity.Login;
import com.nika.chat.service.LoginService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 16:10 2018/09/13
 * @Author Nika
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public Login login(@RequestBody Login login) {

        return loginService.login(login);
    }

}
