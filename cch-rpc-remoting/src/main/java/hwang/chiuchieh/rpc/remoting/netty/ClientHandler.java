package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcResponseBody;
import hwang.chiuchieh.rpc.remoting.util.InvocationUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<RpcContext<RpcResponseBody>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcContext<RpcResponseBody> msg) throws Exception {
        RpcResponseBody body = msg.getBody();
        InvocationUtils.addResult(msg.getRequestId(), body.getReturnVal());
    }
}
