package hwang.chiuchieh.rpc.test.spitest;

import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.test.api.SPITest;

public class SPITestImpl implements SPITest {
    public String testMethod(SPIExt spiExt) {
        return "This is a spi-test string";
    }
}
