package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.protocol.api.Protocol;
import hwang.chiuchieh.rpc.protocol.api.ProxyFactory;
import hwang.chiuchieh.rpc.registry.api.Registry;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.List;

public class ServiceFactory {
    /**
     * 协议实现，通过自适应扩展机制获取
     */
    private static Protocol PROTOCOL =
            ExtensionLoader.getExtesionLoader(Protocol.class).getAdaptiveExtension();

    /**
     * 注册中心实现，通过自适应扩展机制获取
     */
    private static Registry REGISTRY =
            ExtensionLoader.getExtesionLoader(Registry.class).getAdaptiveExtension();

    /**
     * 代理工厂
     */
    private static ProxyFactory PROXY_FACTORY =
            ExtensionLoader.getExtesionLoader(ProxyFactory.class).getAdaptiveExtension();

    /**
     * 服务导出入口
     *
     * 服务导出的作用是将cch-rpc的本地服务暴露出去，使得其他应用能够引用该服务。
     */
    public static <T> void export(Provider<T> provider) {

        SPIExt spiExt = getProviderSPIExt(provider);

        //进行服务导出
        PROTOCOL.export(provider, spiExt);

        //进行服务注册
        REGISTRY.registry(provider, spiExt);
    }

    /**
     * 服务引用入口
     *
     * 服务引用的作用是从远处注册中心获取服务地址，并在本地生成代理，指向远程服务。
     */
    public static <T> T refer(Invoker<T> invoker) {

        SPIExt spiExt = getInvokerSPIExt(invoker);

        //从注册中心获取远程服务地址
        List<RemoteInfo> remoteInfos = REGISTRY.getRemotes(invoker, spiExt);

        //进行服务导出
        Object service = PROTOCOL.refer(invoker, remoteInfos, spiExt);

        return (T)service;
    }


    private static SPIExt getProviderSPIExt(Provider provider) {
        SPIExt spiExt = new SPIExt();
        //填充SPI路由信息
        spiExt.put(SPIExt.SPI_PROTOCOL, provider.getProtocol());
        spiExt.put(SPIExt.SPI_REGISTRY, provider.getRegistry());
        return spiExt;
    }

    private static SPIExt getInvokerSPIExt(Invoker invoker) {
        SPIExt spiExt = new SPIExt();
        //填充SPI路由信息
        spiExt.put(SPIExt.SPI_PROTOCOL, invoker.getProtocol());
        spiExt.put(SPIExt.SPI_PROXY_FACTORY, invoker.getProtocol());
        spiExt.put(SPIExt.SPI_REGISTRY, invoker.getRegistry());
        return spiExt;
    }
}
