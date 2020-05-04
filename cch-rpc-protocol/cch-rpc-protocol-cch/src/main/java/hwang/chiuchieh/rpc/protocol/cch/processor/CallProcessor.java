package hwang.chiuchieh.rpc.protocol.cch.processor;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.protocol.api.CchProcessor;
import hwang.chiuchieh.rpc.remoting.api.Client;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

public class CallProcessor extends CchProcessor {

    /**
     * 客户实现，通过自适应扩展机制获取
     */
    private static Client CLIENT =
            ExtensionLoader.getExtensionLoader(Client.class).getAdaptiveExtension();

    public Object process(Invocation invocation) {
        SPIExt spiExt = getSPIExt(invocation);
        return CLIENT.invoke(invocation, spiExt);
    }

    private SPIExt getSPIExt(Invocation invocation) {
        SPIExt spiExt = new SPIExt();
        spiExt.put(SPIExt.SPI_CLIENT, invocation.getInvoker().getTransport());
        return spiExt;
    }

}
