package hwang.chiuchieh.rpc.remoting.cchprotocol;

import lombok.Data;

@Data
public class RpcResponseBody implements Body {

    private Boolean success;

    private Boolean hasReturnVal;

    private Object returnVal;

    private Integer failCode;

    private String failDesc;

    private Throwable throwable;

}
