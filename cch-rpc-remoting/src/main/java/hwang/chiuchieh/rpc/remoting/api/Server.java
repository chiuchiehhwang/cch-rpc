package hwang.chiuchieh.rpc.remoting.api;

import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

@SPI("netty")
public interface Server {

    boolean openServer(String port, SPIExt spiExt);

}
