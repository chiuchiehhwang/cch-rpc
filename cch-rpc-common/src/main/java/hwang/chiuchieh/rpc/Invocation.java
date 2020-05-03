package hwang.chiuchieh.rpc;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class Invocation {

    private Invoker invoker;

    private String methodName;

    private Class<?>[] argsTypes;

    private Class<?> returnType;

    private Object[] args;

    private String host;

    private Integer port;

}
