package com.nika.chat.handler;

import com.nika.chat.common.entity.MsgData;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * I/O事件处理器
 * @Date 09:42 2018/09/04
 * @Author Nika
 */
@Sharable
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<MsgData> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 每当从服务端收到新的客户端连接时，客户端的 Channel 存入 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
     * @Date 09:54 2018/09/04
     * @Author Nika
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        // 向多个频道广播消息
        channels.writeAndFlush("[Server] - " + incoming.remoteAddress() + "加入\n");
        channels.add(ctx.channel());
    }

    /**
     * 每当从服务端收到客户端断开时，客户端的 Channel 自动从 ChannelGroup 列表中移除了，并通知列表中的其他客户端 Channel
     * @Date 09:54 2018/09/04
     * @Author Nika
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        // 向多个频道广播消息
        channels.writeAndFlush("[Server] - " + incoming.remoteAddress() + "离开\n");
    }

    /**
     * 每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel
     * @Date 09:54 2018/09/04
     * @Author Nika
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MsgData msgData) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels){
            if (channel != incoming){
                channel.writeAndFlush("[ " + incoming.remoteAddress() + " ] " + msgData.getContext() + "\n");
            } else {
                channel.writeAndFlush("[ you ] " + msgData.getContext() + "\n");
            }
        }
    }

    /**
     * 服务端监听到客户端活动
     * @Date 09:54 2018/09/04
     * @Author Nika
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
    }

    /**
     * 服务端监听到客户端不活动
     * @Date 09:55 2018/09/04
     * @Author Nika
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        if(incoming.isActive())ctx.close();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
