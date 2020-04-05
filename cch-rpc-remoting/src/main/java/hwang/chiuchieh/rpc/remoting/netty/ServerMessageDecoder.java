package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.cchprotocol.Body;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.SerializationType;
import hwang.chiuchieh.rpc.remoting.exception.MagicIncorrectException;
import hwang.chiuchieh.rpc.remoting.serialization.GsonSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ServerMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        short magicNum = in.readShort();
        if (magicNum != 0xcce3) {
            //TODO
            throw new MagicIncorrectException();
        }
        byte msgTypeAndSerialization = in.readByte();
        int msgTypeCode = (msgTypeAndSerialization & 0xe0) >>> 5;
        int serializationId = msgTypeAndSerialization & 0x1f;
        MsgType msgType = MsgType.getByCode(msgTypeCode);
        SerializationType serializationType = SerializationType.getByCode(serializationId);
        Long requestId = in.readLong();
        Integer bodyLength = in.readInt();
        byte[] bodyByte = new byte[bodyLength];
        in.readBytes(bodyByte, 0, bodyLength);
        Body body = getBody(msgType, serializationType, bodyByte);

        RpcContext<Body> rpcContext = new RpcContext<>();
        rpcContext.setMsgType(msgType);
        rpcContext.setSerializationType(serializationType);
        rpcContext.setRequestId(requestId);
        rpcContext.setBodyLength(bodyLength);
        rpcContext.setBody(body);

        out.add(rpcContext);
    }

    private Body getBody(MsgType msgType, SerializationType sType, byte[] bodyByte) {
        switch (sType) {
            case gson:
                return GsonSerialization.deserialize(msgType, bodyByte);
            default:
                return null;
        }
    }
}