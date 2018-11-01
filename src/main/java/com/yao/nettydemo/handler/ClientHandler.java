package com.yao.nettydemo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * Description: 客户端事件处理类
 * User: admin
 * Date: 2018-11-01
 * Time: 19:28
 */
public class ClientHandler extends SimpleChannelInboundHandler {
    /**
     * 读取到消息时触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        System.out.println("收到啦"+buf.toString(Charset.forName("UTF-8")));
    }

    /**
     * 连接上时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("我来啦".getBytes()));//发送上线消息
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("我错了"+cause.getMessage());
    }
}
