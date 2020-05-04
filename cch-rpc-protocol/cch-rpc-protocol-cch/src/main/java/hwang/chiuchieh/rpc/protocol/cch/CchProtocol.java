package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.protocol.api.AbstractProtocol;
import hwang.chiuchieh.rpc.protocol.api.ProxyFactory;
import hwang.chiuchieh.rpc.remoting.netty.NettyServer;
import hwang.chiuchieh.rpc.remoting.util.InvocationUtils;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CchProtocol extends AbstractProtocol {

    /**
     * 代理工厂
     */
    private static ProxyFactory PROXY_FACTORY =
            ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    @Override
    public void export(Provider provider, SPIExt spiExt) {
        NettyServer nettyServer = new NettyServer(provider.getPort());
        InvocationUtils.addProvider(provider);
        nettyServer.openServer();
    }

    @Override
    public <T> T refer(Invoker<T> invoker, SPIExt spiExt) {
        return PROXY_FACTORY.getProxy(invoker, spiExt);
    }
}
