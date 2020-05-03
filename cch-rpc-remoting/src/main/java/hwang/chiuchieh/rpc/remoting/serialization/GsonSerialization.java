package hwang.chiuchieh.rpc.remoting.serialization;

import com.google.gson.Gson;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.remoting.api.Serialization;
import hwang.chiuchieh.rpc.remoting.cchprotocol.Body;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.spi.SPIExt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GsonSerialization implements Serialization {

    public Body deserialize(MsgType msgType, byte[] bodyByte, SPIExt spiExt) {
        try {
            switch (msgType) {
                case RequestRpc:
                    return new Gson().fromJson(new String(bodyByte, "UTF-8"), RpcRequestBody.class);
                default:
                    return null;
            }
        } catch (Exception e) {
            log.error("反序列化时发生异常", e);
            throw new CchRpcException(e);
        }

    }

    public byte[] serialize(MsgType msgType, Body body, SPIExt spiExt) {
        try {
            switch (msgType) {
                case ResponseRpc:
                    return new Gson().toJson(body).getBytes("UTF-8");
                default:
                    return null;
            }
        } catch (Exception e) {
            log.error("序列化时发生异常", e);
            throw new CchRpcException(e);
        }
    }

}
