package hwang.chiuchieh.rpc.remoting.netty;

import com.google.gson.Gson;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcContext;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.util.RpcUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientMessageEncoder extends MessageToByteEncoder<RpcContext<RpcRequestBody>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcContext<RpcRequestBody> msg, ByteBuf out) throws Exception {
        int magicNum = 0xcce3;
        int msgTypeCode = msg.getMsgType().getCode();
        int serializationId = msg.getSerializationType().getCode();
        byte msgTypeAndSerializationType = ((Integer) ((msgTypeCode << 5) | serializationId)).byteValue();
        long requestId = msg.getRequestId();
//        byte[] bodyByte = RpcUtils.getBodyByte(msg.getMsgType(), msg.getSerializationType(), msg.getBody());
        RpcRequestBody body = msg.getBody();
        String str = new Gson().toJson(body);
        byte[] bodyByte = str.getBytes();
        int bodyLength = bodyByte.length;

        out.writeShort(magicNum);
        out.writeByte(msgTypeAndSerializationType);
        out.writeLong(requestId);
        out.writeInt(bodyLength);
        out.writeBytes(bodyByte);
    }
}
