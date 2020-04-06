package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.protocol.api.ProxyFactory;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.protocol.api.Protocol;
import hwang.chiuchieh.rpc.api.Invoker;
import hwang.chiuchieh.rpc.registry.api.Registry;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.util.CollectionUtils;
import hwang.chiuchieh.rpc.util.StringUtils;
import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    private static ProxyFactory PROXY_FACTORY =
            ExtensionLoader.getExtesionLoader(ProxyFactory.class).getAdaptiveExtension();

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
        Invoker<T> invoker = PROXY_FACTORY.getInvoker(interfaceName, ref, info);

        //进行服务导出
        PROTOCOL.export(invoker, info);

        //进行服务注册
        REGISTRY.registry(info);
    }

    private void checkConfig() {
        if(StringUtils.isBlank(interfaceName)) {
            throw new CchRpcException("interface name is blank");
        }
        if(ref == null) {
            throw new CchRpcException("no instance of the class");
        }
        checkApplicationConfig();
        checkProtocolConfig();
        checkRegistryConfig();
    }

    private void checkApplicationConfig() {
        if(applicationConfig == null) {
            throw new CchRpcException("no application's configuration");
        }
        if(StringUtils.isBlank(applicationConfig.getName())) {
            throw new CchRpcException("application name is blank");
        }
    }

    private void checkProtocolConfig() {
        if(protocolConfig == null) {
            protocolConfig = new ProtocolConfig();
        }
        if(StringUtils.isBlank(protocolConfig.getName())) {
            protocolConfig.setName(ProtocolConfig.DEFAULT_PROTOCOL);
        }
        if(StringUtils.isBlank(protocolConfig.getPort())) {
            protocolConfig.setPort(ProtocolConfig.DEFAULT_PROTOCOL_PORT);
        } else {
            try {
                int port = Integer.valueOf(protocolConfig.getPort());
                if (port <= 1023) {
                    throw new CchRpcException("port < 1024");
                }
            } catch (NumberFormatException e) {
                throw new CchRpcException("port is invalid");
            }
        }
    }

    private void checkRegistryConfig() {
        if(registryConfig == null) {
            throw new CchRpcException("no registry's configuration");
        }
        if(StringUtils.isBlank(registryConfig.getName())) {
            registryConfig.setName(RegistryConfig.DEFAULT_REGISTRY);
        }
        Set<String> registries = getRegistries();
        if (CollectionUtils.isEmpty(registries)) {
            throw new CchRpcException("no registry's address");
        }
    }

    private Info generateInfo() {
        String host;
        try {
            //获取本地IP
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new CchRpcException("can't get local address", e);
        }
        String port = protocolConfig.getPort();
        String path = host + Info.PATH_SEPARATOR + port + Info.PATH_SEPARATOR + interfaceName;

        Set<String> registries = getRegistries();

        Info info = new Info();
        info.setHost(host);
        info.setPort(port);
        info.setPath(path);
        info.setServiceName(interfaceName);
        info.setRegistries(new ArrayList<>(registries));

        //填充SPI路由信息
        info.put(Info.SPI_PROTOCOL, protocolConfig.getName());
        info.put(Info.SPI_PROXY_FACTORY, protocolConfig.getName());
        info.put(Info.SPI_REGISTRY, registryConfig.getName());

        return info;
    }

    private Set<String> getRegistries() {
        Set<String> registries = new HashSet<>();
        if (registryConfig == null) {
            return registries;
        }
        if (StringUtils.isNotBlank(registryConfig.getHost())
                && StringUtils.isNotBlank(registryConfig.getPort())) {
            registries.add(registryConfig.getHost()
                    + RegistryConfig.HOST_NAME_SEPARATOR
                    + registryConfig.getPort());
        }
        String addresses = registryConfig.getAddresses();
        if (StringUtils.isNotBlank(addresses)) {
            String[] addrArray = addresses.split(RegistryConfig.ADDRESS_SEPARATOR);
            for (String addr : addrArray) {
                if (StringUtils.isNotBlank(addr)) {
                    registries.add(addr);
                }
            }
        }
        return registries;
    }
}
