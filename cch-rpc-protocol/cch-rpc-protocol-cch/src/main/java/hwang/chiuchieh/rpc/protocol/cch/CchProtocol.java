package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.protocol.api.AbstractProtocol;
import hwang.chiuchieh.rpc.protocol.api.ProxyFactory;
import hwang.chiuchieh.rpc.remoting.api.Server;
import hwang.chiuchieh.rpc.remoting.netty.NettyServer;
import hwang.chiuchieh.rpc.remoting.util.InvocationUtils;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CchProtocol extends AbstractProtocol {

    /**
     * 代理工厂实现，通过自适应扩展机制获取
     */
    private static ProxyFactory PROXY_FACTORY =
            ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    /**
     * 服务器实现，通过自适应扩展机制获取
     */
    private static Server SERVER =
            ExtensionLoader.getExtensionLoader(Server.class).getAdaptiveExtension();

    @Override
    public void export(Provider provider, SPIExt spiExt) {
        spiExt.put(SPIExt.SPI_SERVER, provider.getTransport());
        InvocationUtils.addProvider(provider);
        SERVER.openServer(provider.getPort(), spiExt);
    }

    @Override
    public <T> T refer(Invoker<T> invoker, SPIExt spiExt) {
        spiExt.put(SPIExt.SPI_PROXY_FACTORY, invoker.getProxy());
        return PROXY_FACTORY.getProxy(invoker, spiExt);
    }
}
