package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.List;

/**
 * 协议接口
 * 进行服务的暴露与引入
 */
@SPI("cch")
public interface Protocol {

    void export(Provider provider, SPIExt spiExt);

    Object refer(Invoker invoker, List<RemoteInfo> remoteInfos, SPIExt spiExt);
}
