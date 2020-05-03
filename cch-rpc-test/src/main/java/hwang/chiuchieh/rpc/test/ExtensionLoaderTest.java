package hwang.chiuchieh.rpc.test;

import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.test.api.SPITest;
import org.junit.Test;

public class ExtensionLoaderTest {

    @Test
    public void getExtension() {
        //测试SPI机制 获取指定名称扩展
        ExtensionLoader<SPITest> extensionLoader = ExtensionLoader.getExtensionLoader(SPITest.class);
        SPITest testObj = extensionLoader.getExtension("spi-test2");
        String result = testObj.testMethod(null);
        System.out.println(result);

        //测试获取默认扩展
        testObj = extensionLoader.getExtension(null);
        result = testObj.testMethod(null);
        System.out.println(result);
    }

    @Test
    public void getAdaptiveExtension() {
        //测试自适应拓展机制
        ExtensionLoader<SPITest> extensionLoader = ExtensionLoader.getExtensionLoader(SPITest.class);
        SPITest testObj = extensionLoader.getAdaptiveExtension();
        SPIExt spiExt = new SPIExt();
        spiExt.put("extensionSPITest", "spi-test2");
        String result = testObj.testMethod(spiExt);
        System.out.println(result);
    }
}