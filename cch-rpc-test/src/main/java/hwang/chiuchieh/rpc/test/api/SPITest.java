package hwang.chiuchieh.rpc.test.api;


import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

@SPI("spi-test")
public interface SPITest {
    String testMethod(SPIExt spiExt);
}
