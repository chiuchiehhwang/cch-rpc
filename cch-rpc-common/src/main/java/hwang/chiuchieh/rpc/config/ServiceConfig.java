package hwang.chiuchieh.rpc.config;

import lombok.Data;


@Data
public class ServiceConfig<T> {

    /**
     * 接口名称
     */
    String interfaceName;

    /**
     * 引用的接口实例
     */
    T ref;

    /**
     * 应用的配置
     */
    ApplicationConfig applicationConfig;

    /**
     * RPC协议的配置
     */
    ProtocolConfig protocolConfig;

    /**
     * 注册中心的配置
     */
    RegistryConfig registryConfig;
}
