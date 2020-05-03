package hwang.chiuchieh.rpc.protocol.api;


import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

@SPI("jdk")
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker, SPIExt spiExt);

}
