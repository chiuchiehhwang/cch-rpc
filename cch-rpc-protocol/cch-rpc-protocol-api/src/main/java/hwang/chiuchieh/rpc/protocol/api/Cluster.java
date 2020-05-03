package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

@SPI("failover")
public interface Cluster {

    Object invoke(Invocation invocation, CchProcessor processor, SPIExt spiExt);

}
