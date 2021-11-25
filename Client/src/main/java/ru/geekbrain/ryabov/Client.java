package ru.geekbrain.ryabov;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import ru.geekbrain.ryabov.handlers.JsonDecoder;
import ru.geekbrain.ryabov.handlers.JsonEncoder;
import ru.geekbrain.ryabov.message.DownloadResourse;

import javax.imageio.stream.MemoryCacheImageOutputStream;

public class Client {
    public static void main(String[] args) {
        try {
            new Client().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void run() throws InterruptedException {
        NioEventLoopGroup work = new NioEventLoopGroup();

            Bootstrap bootstrap = new Bootstrap()
                    .group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(
                                    new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 3, 0, 3),
                                    new LengthFieldPrepender(3),
                                    new JsonEncoder(),
                                    new JsonDecoder(),
                                    new ClientHandler()
                            );
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            Channel channel = bootstrap.connect("localhost", 9001).sync().channel();
            DownloadResourse downloadResourse = new DownloadResourse();
            downloadResourse.setPath("D:\\My work\\JAVA\\DropBox\\Netty-File-Server\\test.json");
            channel.writeAndFlush(downloadResourse);

    }
}
