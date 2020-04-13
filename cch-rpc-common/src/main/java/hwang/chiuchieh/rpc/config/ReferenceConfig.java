package hwang.chiuchieh.rpc.config;

import lombok.Data;

@Data
public class ReferenceConfig {

    String id;

    String interfaceName;

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
