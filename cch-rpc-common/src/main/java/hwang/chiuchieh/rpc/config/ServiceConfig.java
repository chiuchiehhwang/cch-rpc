package hwang.chiuchieh.rpc.config;

import lombok.Data;

/**
 * @param <T> 导出的类的接口类型
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * 服务导出配置
 */
@Data
public class ServiceConfig<T> {

    /**
     * 接口全限定名
     * 可为空，为空时取clazz的全限定名
     */
    String interfaceName;

    /**
     * 暴露的接口类型
     * 【必填】
     */
    Class<T> clazz;

    /**
     * 引用的接口实例
     * 【必填】
     */
    T ref;

    /**
     * 应用的配置
     * 【必填】
     */
    ApplicationConfig applicationConfig;

    /**
     * RPC协议的配置
     * 【必填】
     */
    ProtocolConfig protocolConfig;

    /**
     * 注册中心的配置
     * 【必填】
     */
    RegistryConfig registryConfig;
}
