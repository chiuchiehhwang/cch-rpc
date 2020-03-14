package hwang.chiuchieh.rpc.proxy;

import hwang.chiuchieh.rpc.api.Invoker;

public interface ProxyFactory {
    <T> Invoker<T> getInvoker(String interfaceName, T instance);
}
