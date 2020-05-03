package hwang.chiuchieh.rpc.protocol.cch.filter;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.protocol.api.CchProcessor;
import hwang.chiuchieh.rpc.protocol.api.Cluster;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

public class ClusterProcessor extends CchProcessor {

    /**
     * 集群实现，通过自适应扩展机制获取
     */
    private static Cluster CLUSTER =
            ExtensionLoader.getExtensionLoader(Cluster.class).getAdaptiveExtension();

    @Override
    public Object process(Invocation invocation) {
        SPIExt spiExt = getSPIExt(invocation.getInvoker());
        return CLUSTER.invoke(invocation, this, spiExt);
    }

    private SPIExt getSPIExt(Invoker invoker) {
        SPIExt spiExt = new SPIExt();
        spiExt.put(SPIExt.SPI_REGISTRY, invoker.getRegistry());
        //TODO
        spiExt.put(SPIExt.SPI_CLUSTER, "failover");
        spiExt.put(SPIExt.SPI_LOAD_BALANCE, "consistentHash");
        return spiExt;
    }

}
