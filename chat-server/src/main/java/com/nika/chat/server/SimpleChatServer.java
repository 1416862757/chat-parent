package com.nika.chat.server;

import com.nika.chat.handler.SimpleChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @Date 10:11 2018/09/04
 * @Author Nika
 */
@Component
public class SimpleChatServer {

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();
    private ServerBootstrap bootstrap = new ServerBootstrap();

    /**
     * 关闭服务器的方法
     */
    @PreDestroy
    public void close(){
        System.out.println("关闭服务器。。。");
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    @PreDestroy
    public void run() throws Exception{
        try {
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("SimpleChatSever 启动了");
            // 绑定端口， 开始接收进来的链接
            ChannelFuture future = bootstrap.bind(8080).sync();
            // 等待服务器 socket 关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e){
            System.out.println(" 出现异常， 释放资源 ");
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
