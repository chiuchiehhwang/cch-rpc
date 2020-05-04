package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcResponseBody;
import hwang.chiuchieh.rpc.remoting.util.RpcUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerMessageEncoder extends MessageToByteEncoder<RpcContext<RpcResponseBody>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcContext<RpcResponseBody> msg, ByteBuf out) throws Exception {
        int magicNum = 0xcce3;
        int msgTypeCode = msg.getMsgType().getCode();
        int serializationId = msg.getSerializationType().getCode();
        byte msgTypeAndSerializationType = ((Integer) ((msgTypeCode << 5) | serializationId)).byteValue();
        long requestId = msg.getRequestId();
        byte[] bodyByte = RpcUtils.getBodyByte(msg.getMsgType(), msg.getSerializationType(), msg.getBody());
        int bodyLength = bodyByte.length;

        out.writeShort(magicNum);
        out.writeByte(msgTypeAndSerializationType);
        out.writeLong(requestId);
        out.writeInt(bodyLength);
        out.writeBytes(bodyByte);
    }
}
