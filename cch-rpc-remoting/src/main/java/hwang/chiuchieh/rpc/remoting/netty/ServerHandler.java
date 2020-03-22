package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<RpcContext<RpcRequestBody>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcContext<RpcRequestBody> msg) throws Exception {
        RpcRequestBody body = msg.getBody();
        //TODO
    }
}
