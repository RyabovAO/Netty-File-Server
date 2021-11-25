package ru.geekbrain.ryabov;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.geekbrain.ryabov.message.DownloadResourse;
import ru.geekbrain.ryabov.message.File;
import ru.geekbrain.ryabov.message.Message;

import java.io.RandomAccessFile;
import java.lang.reflect.Member;

public class ServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message msg) throws Exception {
        if(msg instanceof DownloadResourse){
            var message = (DownloadResourse) msg;
            try(RandomAccessFile randomAccessFile = new RandomAccessFile(message.getPath(), "r")){
                File file = new File();
                byte[] bytes = new byte[(int) randomAccessFile.length()];
                randomAccessFile.read(bytes);
                file.setContent(bytes);
                channelHandlerContext.writeAndFlush(file);
            }
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is registered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is unregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is inactive");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("new cause " + cause.getMessage());
        ctx.close();
    }
}
