package ru.geekbrain.ryabov.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import ru.geekbrain.ryabov.message.File;
import ru.geekbrain.ryabov.message.Message;
import ru.geekbrain.ryabov.message.TextMessage;

import java.util.List;

public class JsonDecoder extends MessageToMessageDecoder<ByteBuf> {

protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            byte[] bytes = ByteBufUtil.getBytes(byteBuf);
        System.out.println("New message as String: " + new String(bytes));
        Message message =OBJECT_MAPPER.readValue(bytes, Message.class);
        list.add(message);
        }

}
