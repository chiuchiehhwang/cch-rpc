package hwang.chiuchieh.rpc.test.api;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.SPI;

@SPI("spi-test")
public interface SPITest {
    String testMethod(Info Info);
}
