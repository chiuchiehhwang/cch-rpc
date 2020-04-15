package hwang.chiuchieh.rpc.registry.api;


import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.List;

@SPI("zookeeper")
public interface Registry {

    <T> void registry(Provider<T> provider, SPIExt spiExt);

    <T> List<RemoteInfo> getRemotes(Invoker<T> invoker, SPIExt spiExt);
}
