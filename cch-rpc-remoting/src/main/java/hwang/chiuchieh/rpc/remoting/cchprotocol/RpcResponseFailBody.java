package hwang.chiuchieh.rpc.remoting.cchprotocol;

import lombok.Data;

@Data
public class RpcResponseFailBody extends Body {

    private String failCode;

    private String desc;

}
