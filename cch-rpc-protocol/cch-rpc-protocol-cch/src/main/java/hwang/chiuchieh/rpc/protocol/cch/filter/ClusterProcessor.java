package hwang.chiuchieh.rpc.protocol.cch.filter;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.registry.api.Registry;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.List;

public class ClusterProcessor extends CchProcessor {

    /**
     * 注册中心实现，通过自适应扩展机制获取
     */
    private static Registry REGISTRY =
            ExtensionLoader.getExtesionLoader(Registry.class).getAdaptiveExtension();

    @Override
    public Object process(Invocation invocation) {
        Invoker invoker = invocation.getInvoker();
        SPIExt spiExt = getRegistrySPIExt(invoker);
        List<RemoteInfo> remoteInfos = REGISTRY.getRemotes(invoker, spiExt);


        return invokeNext(this, invocation);
    }

    private SPIExt getRegistrySPIExt(Invoker invoker) {
        SPIExt spiExt = new SPIExt();
        spiExt.put(SPIExt.SPI_REGISTRY, invoker.getRegistry());
        return spiExt;
    }

}
