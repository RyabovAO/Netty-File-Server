package ru.geekbrain.ryabov.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.geekbrain.ryabov.message.Message;

public class JsonEncoder extends MessageToByteEncoder<Message> {
    protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(message);
        byteBuf.writeBytes(bytes);
    }
}
