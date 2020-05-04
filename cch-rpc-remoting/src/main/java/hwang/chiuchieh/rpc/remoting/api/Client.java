package hwang.chiuchieh.rpc.remoting.api;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

@SPI("netty")
public interface Client {

    Object invoke(Invocation invocation, SPIExt spiExt);

}
