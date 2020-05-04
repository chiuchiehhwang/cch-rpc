package hwang.chiuchieh.rpc.remoting.cchprotocol;

import lombok.Data;

@Data
public class RpcRequestBody extends Body {

    private String interfaceName;

    private String methodName;

    private String[] argsTypes;

    private Object[] argsValues;

    private String returnType;

    private String serviceName;

}
