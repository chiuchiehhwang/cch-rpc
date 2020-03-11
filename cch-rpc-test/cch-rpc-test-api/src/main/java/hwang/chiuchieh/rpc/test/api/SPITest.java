package hwang.chiuchieh.rpc.test.api;

@SPI("spi-test")
public interface SPITest {
    String testMethod(Info Info);
}
