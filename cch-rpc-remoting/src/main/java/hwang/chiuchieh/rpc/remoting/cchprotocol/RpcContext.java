package hwang.chiuchieh.rpc.remoting.cchprotocol;

import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.SerializationType;
import lombok.Data;

@Data
public class RpcContext<T extends Body> {

    private MsgType msgType;

    private SerializationType serializationType;

    private Long requestId;

    private T body;
}
