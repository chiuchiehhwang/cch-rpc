package hwang.chiuchieh.rpc.remoting.cchprotocol;

import lombok.Data;

@Data
public class RpcResponseBody extends Body {

    private Boolean success = true;

    private Object returnVal;

    private Integer failCode;

    private String failDesc;

    private Exception exception;

}
