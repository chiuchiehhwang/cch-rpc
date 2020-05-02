package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

/**
 * 协议接口
 * 进行服务的暴露与引入
 */
@SPI("cch")
public interface Protocol {

    <T> void export(Provider<T> provider, SPIExt spiExt);

    <T> T refer(Invoker<T> invoker, SPIExt spiExt);
}
