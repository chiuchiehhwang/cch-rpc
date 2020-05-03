package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.protocol.api.Protocol;
import hwang.chiuchieh.rpc.registry.api.Registry;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceFactory {
    /**
     * 协议实现，通过自适应扩展机制获取
     */
    private static Protocol PROTOCOL =
            ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    /**
     * 注册中心实现，通过自适应扩展机制获取
     */
    private static Registry REGISTRY =
            ExtensionLoader.getExtensionLoader(Registry.class).getAdaptiveExtension();

    /**
     * 服务导出入口
     * <p>
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
     * 服务引入入口
     * <p>
     * 服务引入的作用是从远处注册中心获取服务地址，并在本地生成代理，指向远程服务。
     */
    public static <T> T refer(Invoker<T> invoker) {
        SPIExt spiExt = getInvokerSPIExt(invoker);

        //进行服务引入
        return PROTOCOL.refer(invoker, spiExt);
    }


    private static <T> SPIExt getProviderSPIExt(Provider<T> provider) {
        SPIExt spiExt = new SPIExt();
        //填充SPI路由信息
        spiExt.put(SPIExt.SPI_PROTOCOL, provider.getProtocol());
        spiExt.put(SPIExt.SPI_REGISTRY, provider.getRegistry());
        return spiExt;
    }

    private static <T> SPIExt getInvokerSPIExt(Invoker<T> invoker) {
        SPIExt spiExt = new SPIExt();
        //填充SPI路由信息
        spiExt.put(SPIExt.SPI_PROTOCOL, invoker.getProtocol());
        return spiExt;
    }
}
