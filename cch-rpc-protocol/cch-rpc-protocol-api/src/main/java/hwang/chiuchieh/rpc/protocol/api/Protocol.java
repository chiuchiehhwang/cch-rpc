package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

/**
 * 协议接口
 * 进行服务的暴露与引入
 */
@SPI("cch")
public interface Protocol {
    void export(Provider provider, SPIExt spiExt);
}
