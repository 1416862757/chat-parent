package com.nika.chat.service;

import com.nika.chat.common.entity.Login;
import com.nika.chat.common.entity.MsgData;

/**
 * @Date 16:52 2018/09/13
 * @Author Nika
 */
public interface LoginService {

    Login login(Login login);

    void sendMsg(MsgData msg);

}
