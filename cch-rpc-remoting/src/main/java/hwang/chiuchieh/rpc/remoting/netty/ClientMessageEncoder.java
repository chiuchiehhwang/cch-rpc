package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientMessageEncoder extends MessageToByteEncoder<RpcContext> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcContext msg, ByteBuf out) throws Exception {

    }
}
