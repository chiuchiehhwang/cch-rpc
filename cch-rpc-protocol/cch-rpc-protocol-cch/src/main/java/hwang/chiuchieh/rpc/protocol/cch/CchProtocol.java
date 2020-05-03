package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.protocol.api.AbstractProtocol;
import hwang.chiuchieh.rpc.protocol.api.ProxyFactory;
import hwang.chiuchieh.rpc.remoting.netty.NettyServer;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CchProtocol extends AbstractProtocol {

    protected static Map<String, Provider> providerMap = new ConcurrentHashMap<>();

    /**
     * 代理工厂
     */
    private static ProxyFactory PROXY_FACTORY =
            ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    @Override
    public void export(Provider provider, SPIExt spiExt) {
        NettyServer nettyServer = new NettyServer(provider.getPort());
        nettyServer.openServer();
        Provider existProvider = providerMap.get(provider.getInterfaceName());
        if (existProvider == null) {
            providerMap.putIfAbsent(provider.getInterfaceName(), provider);
        }
    }

    @Override
    public <T> T refer(Invoker<T> invoker, SPIExt spiExt) {
        spiExt.put(SPIExt.SPI_PROXY_FACTORY, invoker.getProtocol());

        return PROXY_FACTORY.getProxy(invoker, spiExt);
    }
}
