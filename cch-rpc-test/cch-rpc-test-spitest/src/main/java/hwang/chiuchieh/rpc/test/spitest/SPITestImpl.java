package hwang.chiuchieh.rpc.test.spitest;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.test.api.SPITest;

public class SPITestImpl implements SPITest {
    public String testMethod(Info Info) {
        return "This is a spi-test string";
    }
}
