package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.remoting.api.Server;
import hwang.chiuchieh.rpc.spi.SPIExt;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class NettyServer implements Server {

    ServerHandler handler = new ServerHandler();
    ServerMessageDecoder decoder = new ServerMessageDecoder();
    ServerMessageEncoder encoder = new ServerMessageEncoder();

    EventLoopGroup group = new NioEventLoopGroup();

    private static Map<String, NettyServer> serverMap = new ConcurrentHashMap<>();

    @Override
    public boolean openServer(String port, SPIExt spiExt) {
        if (serverMap.get(port) != null) {
            return true;
        }
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
                serverMap.put(port, this);
                return true;
            }
        } catch (InterruptedException e) {
            log.error("等待Netty服务器启动过程中被中断");
            throw new CchRpcException(e);
        }
        return false;
    }
}
