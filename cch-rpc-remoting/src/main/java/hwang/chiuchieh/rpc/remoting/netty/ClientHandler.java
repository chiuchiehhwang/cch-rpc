package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcResponseSuccessBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<RpcContext<RpcResponseSuccessBody>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcContext<RpcResponseSuccessBody> msg) throws Exception {

    }
}
