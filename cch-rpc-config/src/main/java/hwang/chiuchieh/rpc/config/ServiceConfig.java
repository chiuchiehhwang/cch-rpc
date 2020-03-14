package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.Protocol;
import hwang.chiuchieh.rpc.api.Registry;
import hwang.chiuchieh.rpc.proxy.CchProxyFactory;
import hwang.chiuchieh.rpc.api.Invoker;
import hwang.chiuchieh.rpc.proxy.ProxyFactory;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import lombok.Data;

@Data
public class ServiceConfig<T> {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 引用的接口实例
     */
    private T ref;

    /**
     * 应用的配置
     */
    private ApplicationConfig applicationConfig;

    /**
     * RPC协议的配置
     */
    private ProtocolConfig protocolConfig;

    /**
     * 注册中心的配置
     */
    private RegistryConfig registryConfig;

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
    private static ProxyFactory PROXY_FACTORY = new CchProxyFactory();

    /**
     * 服务导出入口
     *
     * 服务导出的作用是将cch-rpc的本地服务暴露出去，使得其他应用能够引用该服务。
     *
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
        Invoker<T> invoker = PROXY_FACTORY.getInvoker(interfaceName, ref);

        //进行服务导出
        PROTOCOL.export(invoker, info);

        //进行服务注册
        REGISTRY.registry(info);
    }

    private void checkConfig() {
        if(interfaceName == null || interfaceName.length() == 0) {
            throw new IllegalArgumentException("interface name is blank");
        }
        if(ref == null) {
            throw new IllegalArgumentException("no instance of the class");
        }
        checkApplicationConfig();
        checkProtocolConfig();
        checkRegistryConfig();
    }

    private void checkApplicationConfig() {
        if(applicationConfig == null) {
            throw new IllegalArgumentException("no application's configuration");
        }
        if(applicationConfig.getName() == null || applicationConfig.getName().length() == 0) {
            throw new IllegalArgumentException("application name is blank");
        }
    }

    private void checkProtocolConfig() {
        if(protocolConfig == null) {
            protocolConfig = new ProtocolConfig();
        }
        if(protocolConfig.getName() == null || protocolConfig.getName().length() == 0) {
            protocolConfig.setName(ProtocolConfig.DEFAULT_PROTOCOL);
        }
    }

    private void checkRegistryConfig() {
        if(registryConfig == null) {
            throw new IllegalArgumentException("no registry's configuration");
        }
        if(registryConfig.getName() == null || registryConfig.getName().length() == 0) {
            registryConfig.setName(RegistryConfig.DEFAULT_REGISTRY);
        }
    }

    private Info generateInfo() {
        return null;
    }

}
