package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcResponseBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.util.InvocationUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<RpcContext<RpcRequestBody>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcContext<RpcRequestBody> msg) throws Exception {
        RpcRequestBody body = msg.getBody();
        RpcResponseBody responseBody = InvocationUtils.invoke(body);

        RpcContext<RpcResponseBody> rpcContext = new RpcContext<>();
        rpcContext.setBody(responseBody);
        rpcContext.setMsgType(MsgType.RpcResponse);
        rpcContext.setSerializationType(msg.getSerializationType());
        rpcContext.setRequestId(msg.getRequestId());

        ctx.writeAndFlush(rpcContext);
    }
}
