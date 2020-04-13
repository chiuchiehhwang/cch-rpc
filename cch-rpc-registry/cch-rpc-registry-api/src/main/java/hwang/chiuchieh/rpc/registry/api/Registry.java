package hwang.chiuchieh.rpc.registry.api;


import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.List;

@SPI("zookeeper")
public interface Registry {

    void registry(Provider provider, SPIExt spiExt);

    List<RemoteInfo> getRemotes(Invoker invoker, SPIExt spiExt);
}
