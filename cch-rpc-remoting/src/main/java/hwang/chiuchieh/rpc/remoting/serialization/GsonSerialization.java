package hwang.chiuchieh.rpc.remoting.serialization;

import com.google.gson.Gson;
import hwang.chiuchieh.rpc.remoting.cchprotocol.Body;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;

public class GsonSerialization {
    public static Body deserialize(MsgType msgType, byte[] bodyByte) {
        switch (msgType) {
            case RequestRpc:
                return new Gson().fromJson(new String(bodyByte), RpcRequestBody.class);
            default:
                return null;
        }
    }

    public static byte[] serialize(MsgType msgType, Body body) {
        switch (msgType) {
            case ResponseRpcSuccess:
                return new Gson().toJson(body).getBytes();
            default:
                return null;
        }
    }
}
