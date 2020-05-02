package hwang.chiuchieh.rpc.config;

import lombok.Data;

@Data
public class ReferenceConfig<T> {

    /**
     * Spring id
     */
    String id;

    /**
     * 引用的接口名
     */
    String interfaceName;

    /**
     * 引用的接口类型
     */
    Class<T> clazz;

    /**
     * 应用的配置, 必填
     */
    ApplicationConfig applicationConfig;

    /**
     * RPC协议的配置, 必填
     */
    ProtocolConfig protocolConfig;

    /**
     * 注册中心的配置, 必填
     */
    RegistryConfig registryConfig;

}
