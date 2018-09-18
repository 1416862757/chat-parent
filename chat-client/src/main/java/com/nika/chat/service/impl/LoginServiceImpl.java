package com.nika.chat.service.impl;

import com.nika.chat.client.SimpleChatClient;
import com.nika.chat.common.entity.Login;
import com.nika.chat.common.entity.MsgData;
import com.nika.chat.handler.SimpleChatClientInitializer;
import com.nika.chat.service.LoginService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 16:53 2018/09/13
 * @Author Nika
 */
@Service
public class LoginServiceImpl implements LoginService {
    public Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private static Map<String, Login> loginUser = new HashMap<>();

    private AtomicInteger id = new AtomicInteger(1);

    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();

    private static SocketChannel channel = null;

    @Override
    public Login login(Login login) {
        login.setUserId(id.getAndIncrement());
        loginUser.put(login.getUserName(), login);
        logger.info("用户{}登陆成功", login.getUserName());

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .handler(new SimpleChatClientInitializer());
                    ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
                    if (future.isSuccess()){
                        logger.info("连接成功。。。");
                        channel = (SocketChannel) future.channel();
                    } else {
                        logger.info("连接失败。。。");
                    }
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //优雅地退出，释放相关资源
                    group.shutdownGracefully();
                }
            }
        });
        t1.start();
        return login;
    }

    @Override
    public void sendMsg(MsgData msg) {
        if (channel != null){
            channel.writeAndFlush(msg + "\n");
        }
    }


}
