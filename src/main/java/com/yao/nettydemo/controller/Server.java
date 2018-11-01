package com.yao.nettydemo.controller;

import com.yao.nettydemo.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: admin
 * Date: 2018-11-01
 * Time: 19:37
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        ServerHandler serverHandler=new ServerHandler();
        //接收处理新的客户端NIO连接
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();//创建server
            bootstrap.group(group)//绑定连接处理group
                    .localAddress(new InetSocketAddress((5555)))
                    .channel(NioServerSocketChannel.class)//使用NIO连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);//每当新的连接，会创建一个channel，并且添加一个serverHandler 处理事件
                        }
                    });
            ChannelFuture f = bootstrap.bind().sync();//异步绑定服务器，调用sync阻塞直到绑定完成
            f.channel().closeFuture().sync();//获取channel的closeFuture（关闭时返回）

        }finally {
            group.shutdownGracefully();
        }

    }
}
