package hwang.chiuchieh.rpc.remoting.util;

import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.remoting.api.Serialization;
import hwang.chiuchieh.rpc.remoting.cchprotocol.Body;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcResponseBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.FailType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.SerializationType;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.UUID;

public class RpcUtils {

    /**
     * 代理工厂
     */
    private static Serialization SERIALIZATION =
            ExtensionLoader.getExtensionLoader(Serialization.class).getAdaptiveExtension();

    public static Body getBody(MsgType msgType, SerializationType sType, byte[] bodyByte) {
        SPIExt spiExt = new SPIExt();
        spiExt.put(SPIExt.SPI_SERIALIZATION, sType.name());

        return SERIALIZATION.deserialize(msgType, bodyByte, spiExt);
    }

    public static byte[] getBodyByte(MsgType msgType, SerializationType sType, Body body) {
        SPIExt spiExt = new SPIExt();
        spiExt.put(SPIExt.SPI_SERIALIZATION, sType.name());

        return SERIALIZATION.serialize(msgType, body, spiExt);
    }

    public static RpcResponseBody getFailBody(FailType failType) {
       return getFailBody(failType, null);
    }

    public static RpcResponseBody getFailBody(FailType failType, Throwable e) {
        RpcResponseBody body = new RpcResponseBody();
        body.setSuccess(false);
        body.setFailCode(failType.getCode());
        body.setFailDesc(failType.getDesc());
        if (e != null) {
            body.setException(new CchRpcException(e));
        }
        return body;
    }

    public static RpcResponseBody getSuccessBody(Object result) {
        RpcResponseBody body = new RpcResponseBody();
        body.setReturnVal(result);
        return body;
    }

    public static long getRequestId() {
        String uuid = UUID.randomUUID().toString();
        byte[] uuidBytes = uuid.getBytes();
        long requestId = 0;
        int index = 0;
        for (; index <= uuidBytes.length - 8; index++) {
            requestId = requestId ^ getLongValue(index, uuidBytes);
        }
        if (index > uuidBytes.length - 8) {
            requestId = requestId ^ getLongValue(uuidBytes.length - 8, uuidBytes);
        }
        requestId = requestId ^ System.currentTimeMillis();
        return requestId;
    }

    private static long getLongValue(int index, byte[] bytes) {
        long index1 = (bytes[index] & 0xFF) << 56;
        long index2 = (bytes[index + 1] & 0xFF) << 48;
        long index3 = (bytes[index + 2] & 0xFF) << 40;
        long index4 = (bytes[index + 3] & 0xFF) << 32;
        long index5 = (bytes[index + 4] & 0xFF) << 24;
        long index6 = (bytes[index + 5] & 0xFF) << 16;
        long index7 = (bytes[index + 6] & 0xFF) << 8;
        long index8 = (bytes[index + 7] & 0xFF);
        return index1 | index2 | index3 | index4 | index5 | index6 | index7 | index8;
    }

}
