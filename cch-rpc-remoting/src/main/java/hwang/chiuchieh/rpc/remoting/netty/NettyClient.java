package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.SerializationType;
import hwang.chiuchieh.rpc.remoting.util.InvocationUtils;
import hwang.chiuchieh.rpc.remoting.util.RpcUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class NettyClient {
    ClientHandler handler = new ClientHandler();
    ClientMessageDecoder decoder = new ClientMessageDecoder();
    ClientMessageEncoder encoder = new ClientMessageEncoder();

    EventLoopGroup group = new NioEventLoopGroup();

    Bootstrap bootstrap;

    public Object invoke(Invocation invocation) {
        if (bootstrap == null) {
            init();
        }
        Channel channel = bootstrap.connect(invocation.getHost(), invocation.getPort()).channel();
        RpcContext rpcContext = getRpcContext(invocation);

        long requestId = rpcContext.getRequestId();
        BlockingQueue<Object> queue = InvocationUtils.getWaitQueue(requestId);

        channel.writeAndFlush(rpcContext);

        try {
            Object result = queue.take();
            return result;
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }

    private synchronized void init() {
        if (bootstrap == null) {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(encoder);
                            ch.pipeline().addLast(decoder);
                            ch.pipeline().addLast(handler);
                        }
                    });
        }
    }

    private RpcContext getRpcContext(Invocation invocation) {

        RpcRequestBody body = new RpcRequestBody();
        body.setInterfaceName(invocation.getInvoker().getInterfaceName());
        body.setMethodName(invocation.getMethodName());
        body.setArgsTypes(getArgsStrTypes(invocation.getArgsTypes()));
        body.setArgsValues(invocation.getArgsValues());
        body.setReturnType(invocation.getReturnType().getName());
        body.setServiceName(invocation.getInvoker().getServiceName());

        RpcContext<RpcRequestBody> rpcContext = new RpcContext<>();
        rpcContext.setMsgType(MsgType.RpcRequest);
        rpcContext.setSerializationType(SerializationType.gson);
        rpcContext.setRequestId(RpcUtils.getRequestId());
        rpcContext.setBody(body);

        return rpcContext;
    }

    private String[] getArgsStrTypes(Class<?>[] argsTypes) {
        if (argsTypes == null) {
            return new String[0];
        }
        String[] argsStrTypes = new String[argsTypes.length];
        for (int i = 0; i < argsTypes.length; i++) {
            argsStrTypes[i] = argsTypes[i].getName();
        }
        return argsStrTypes;
    }
}
