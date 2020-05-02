package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class NettyClient {
    ClientHandler handler = new ClientHandler();
    ClientMessageDecoder decoder = new ClientMessageDecoder();
    ClientMessageEncoder encoder = new ClientMessageEncoder();

    EventLoopGroup group = new NioEventLoopGroup();

    public boolean invoke(Invocation invocation) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(invocation.getHost(), invocation.getPort()))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(encoder);
                        ch.pipeline().addLast(decoder);
                        ch.pipeline().addLast(handler);
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect().sync();
            if (future.isSuccess()) {
                return true;
            }
        } catch (InterruptedException e) {
            log.error("等待Netty服务器启动过程中被中断");
        }
        return false;
    }
}
