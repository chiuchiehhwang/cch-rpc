package hwang.chiuchieh.rpc.remoting.cchprotocol;

import lombok.Data;

@Data
public class RpcRequestBody extends Body {

    private String interfaceName;

    private String method;

    private String[] paramTypes;

    private String[] paramValues;

}
