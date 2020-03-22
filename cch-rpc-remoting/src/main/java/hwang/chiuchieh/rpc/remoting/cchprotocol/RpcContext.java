package hwang.chiuchieh.rpc.remoting.cchprotocol;

import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.SerializationType;
import lombok.Data;

@Data
public class RpcContext<T extends Body> {

    protected MsgType msgType;

    protected SerializationType serializationType;

    protected Long requestId;

    protected Integer bodyLength;

    T body;
}
