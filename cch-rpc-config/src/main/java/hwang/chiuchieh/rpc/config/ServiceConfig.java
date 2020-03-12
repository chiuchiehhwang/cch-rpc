package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.Protocol;
import hwang.chiuchieh.rpc.api.Registry;
import hwang.chiuchieh.rpc.proxy.CchProxyFactory;
import hwang.chiuchieh.rpc.proxy.Proxy;
import hwang.chiuchieh.rpc.proxy.ProxyFactory;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import lombok.Data;

@Data
public class ServiceConfig {

    private String interfaceName;

    private String ref;

    private static Protocol PROTOCOL =
            ExtensionLoader.getExtesionLoader(Protocol.class).getAdaptiveExtension();
    private static Registry REGISTRY =
            ExtensionLoader.getExtesionLoader(Registry.class).getAdaptiveExtension();

    private static ProxyFactory PROXY_FACTORY = new CchProxyFactory();
    /**
     * 服务导出入口
     *
     * 服务导出的作用是将cch-rpc的本地服务暴露出去，使得其他应用能够引用该服务。
     * 服务导出步骤
     * 1.检查服务配置
     * 2.生成服务的代理类
     * 3.导出服务，打开sever，用于接收响应
     * 4.将服务注册到注册中心
     */
    public void export() {

        //检查服务配置
        checkConfig();

        Info info = generateInfo();

        //生成代理类
        Proxy proxy = PROXY_FACTORY.getProxy();

        //进行服务导出
        PROTOCOL.export(info);

        //进行服务注册
        REGISTRY.registry(info);
    }

    private void checkConfig() {

    }

    private Info generateInfo() {
        return null;
    }

}
