package hwang.chiuchieh.rpc.remoting.cchprotocol;

import lombok.Data;

@Data
public class RpcResponseSuccessBody extends Body {

    private Boolean hasReturnVal;

    private Object returnVal;

}
