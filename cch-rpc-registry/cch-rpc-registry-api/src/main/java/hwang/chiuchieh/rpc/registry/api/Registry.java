package hwang.chiuchieh.rpc.registry.api;


import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

@SPI("zookeeper")
public interface Registry {
    void registry(Provider provider, SPIExt spiExt);
}
