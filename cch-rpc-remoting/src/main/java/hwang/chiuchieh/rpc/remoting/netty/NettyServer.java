package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.api.AbstractServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyServer extends AbstractServer {

    ServerHandler handler = new ServerHandler();
    ServerMessageDecoder decoder = new ServerMessageDecoder();
    ServerMessageEncoder encoder = new ServerMessageEncoder();

    EventLoopGroup group = new NioEventLoopGroup();

    public NettyServer(String port) {
        super(port);
    }


    @Override
    public boolean openServer() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(Integer.valueOf(port)))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(encoder);
                        ch.pipeline().addLast(decoder);
                        ch.pipeline().addLast(handler);
                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind().sync();
            if (future.isSuccess()) {
                return true;
            }
        } catch (InterruptedException e) {
            //TODO 打日志
        }
        return false;
    }
}
