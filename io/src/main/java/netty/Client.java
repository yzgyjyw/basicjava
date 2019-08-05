package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));
    public static void main(String[] args) {
//        System.out.println(Runtime.getRuntime().availableProcessors());
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(work).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast("decoder", new StringDecoder());
                    p.addLast("encoder", new StringEncoder());
                    p.addLast();
                }
            });
            // 获取通道连接
            ChannelFuture future = b.connect(HOST, PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
        }

    }
}
