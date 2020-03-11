package hwang.chiuchieh.rpc.spi;

import hwang.chiuchieh.rpc.test.api.Info;
import hwang.chiuchieh.rpc.test.api.SPITest;
import org.junit.Test;

public class ExtensionLoaderTest {

    @Test
    public void getExtension() {
        //测试SPI机制 获取指定名称扩展
        ExtensionLoader<SPITest> extensionLoader = ExtensionLoader.getExtesionLoader(SPITest.class);
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
        ExtensionLoader<SPITest> extensionLoader = ExtensionLoader.getExtesionLoader(SPITest.class);
        SPITest testObj = extensionLoader.getAdaptiveExtension();
        Info Info = new Info();
        Info.put("extensionSPITest", "spi-test2");
        String result = testObj.testMethod(Info);
        System.out.println(result);
    }
}