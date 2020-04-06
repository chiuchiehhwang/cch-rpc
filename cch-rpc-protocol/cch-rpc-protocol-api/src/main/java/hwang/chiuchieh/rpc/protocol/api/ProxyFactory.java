package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.Invoker;

public interface ProxyFactory {
    <T> Invoker<T> getInvoker(String interfaceName, T instance, Info info);
}
