package hwang.chiuchieh.rpc;

import hwang.chiuchieh.rpc.config.ProtocolConfig;
import hwang.chiuchieh.rpc.config.RegistryConfig;
import lombok.Data;

import java.util.List;

/**
 * 封装具体的代理类及相关信息
 * 并实现具体的调用功能
 *
 * @param <T>
 */
@Data
public class Invoker<T> {

    /**
     * 协议类型，如cch
     */
    private String protocol = ProtocolConfig.DEFAULT_PROTOCOL;

    /**
     * 引入的服务
     */
    private String interfaceName;

    /**
     * 引入的服务类型
     */
    private Class<T> clazz;

    /**
     * 注册中心类型，如zookeeper
     */
    private String registry = RegistryConfig.DEFAULT_REGISTRY;

    /**
     * 注册中心地址, host:port
     */
    private List<String> registries;

    /**
     * 应用名
     */
    private String serviceName;
}
