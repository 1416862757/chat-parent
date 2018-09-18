package com.nika.chat.controller;


import com.nika.chat.common.entity.MsgData;
import com.nika.chat.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 10:56 2018/09/13
 * @Author Nika
 */
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/send")
    public void sendMsg(@RequestBody MsgData msg){
        loginService.sendMsg(msg);
    }

}
