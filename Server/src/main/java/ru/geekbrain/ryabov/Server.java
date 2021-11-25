package ru.geekbrain.ryabov;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import ru.geekbrain.ryabov.handlers.JsonDecoder;
import ru.geekbrain.ryabov.handlers.JsonEncoder;

public class Server {
    public static void main(String[] args) {
        try {
            new Server().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(
                                    new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 3, 0, 3),
                                    new LengthFieldPrepender(3),
                                    new JsonEncoder(),
                                    new JsonDecoder(),
                                    new ServerHandler()
                            );
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 2)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

                Channel channel = serverBootstrap.bind("localhost", 9001).sync().channel();
            System.out.println("Server Started");
            channel.closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
