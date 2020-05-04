package hwang.chiuchieh.rpc;

import lombok.Data;

/**
 * RPC调用信息
 */
@Data
public class Invocation<T> {

    private Invoker<T> invoker;

    private String methodName;

    private Class<?>[] argsTypes;

    private Class<?> returnType;

    private Object[] argsValues;

    private String host;

    private Integer port;

}
