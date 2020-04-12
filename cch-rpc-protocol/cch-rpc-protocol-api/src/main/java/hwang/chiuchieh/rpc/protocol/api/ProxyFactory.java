package hwang.chiuchieh.rpc.protocol.api;


import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.spi.SPIExt;

public interface ProxyFactory {

    <T> Invoker<T> getProxy(String interfaceName, T instance, SPIExt spiExt);
}
