package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.Body;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.SerializationType;
import hwang.chiuchieh.rpc.remoting.serialization.GsonSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerMessageEncoder extends MessageToByteEncoder<RpcContext> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcContext msg, ByteBuf out) throws Exception {
        short magicNum = (short) 0xcce3;
        int msgTypeCode = msg.getMsgType().getCode();
        int serializationId = msg.getSerializationType().getCode();
        byte msgTypeAndSerializationType = ((Integer) ((msgTypeCode << 5) | serializationId)).byteValue();
        long requestId = msg.getRequestId();
        byte[] bodyByte = getBodyByte(msg.getMsgType(), msg.getSerializationType(), msg.getBody());
        int bodyLength = bodyByte.length;

        out.writeShort(magicNum);
        out.writeByte(msgTypeAndSerializationType);
        out.writeLong(requestId);
        out.writeInt(bodyLength);
        out.writeBytes(bodyByte);
    }

    private byte[] getBodyByte(MsgType msgType, SerializationType sType, Body body) {
        switch (sType) {
            case gson:
                return GsonSerialization.serialize(msgType, body);
            default:
                return null;
        }
    }
}
