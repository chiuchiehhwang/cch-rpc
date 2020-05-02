package hwang.chiuchieh.rpc.protocol.api;


import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.spi.SPIExt;

public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker, SPIExt spiExt);
}
