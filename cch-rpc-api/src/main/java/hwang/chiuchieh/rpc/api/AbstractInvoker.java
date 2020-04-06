package hwang.chiuchieh.rpc.api;

import lombok.Data;

@Data
public abstract class AbstractInvoker<T> implements Invoker<T> {

    protected T proxy;

    protected String interfaceName;

}
