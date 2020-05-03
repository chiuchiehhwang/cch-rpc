package hwang.chiuchieh.rpc.remoting.cchprotocol;

import lombok.Data;

@Data
public class RpcRequestBody implements Body {

    private String interfaceName;

    private String methodName;

    private Class<?>[] argsTypes;

    private Object[] argsValues;

    private Class<?> returnType;

    private String serviceName;

}
