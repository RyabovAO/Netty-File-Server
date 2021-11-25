package ru.geekbrain.ryabov;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.geekbrain.ryabov.message.File;
import ru.geekbrain.ryabov.message.Message;

import java.io.RandomAccessFile;

public class ClientHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message msg) throws Exception {
        if (msg instanceof File) {
            var message = (File) msg;
            try (RandomAccessFile rw = new RandomAccessFile("1", "rw")) {
                rw.write(message.getContent());
            }
            channelHandlerContext.close();
        }
    }
}
