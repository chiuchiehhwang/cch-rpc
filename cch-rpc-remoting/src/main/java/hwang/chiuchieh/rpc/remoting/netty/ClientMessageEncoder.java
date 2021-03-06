package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.Constant;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.util.RpcUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientMessageEncoder extends MessageToByteEncoder<RpcContext<RpcRequestBody>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcContext<RpcRequestBody> msg, ByteBuf out) throws Exception {
        int magicNum = Constant.MAGIC_NUM;
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
