package hwang.chiuchieh.rpc;

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

    private String protocol;

    private String host;

    private String port;

    private String serviceName;

    /**
     * 注册中心，如zookeeper
     */
    private String registry;
    /**
     * host:port
     */
    private List<String> registries;

    protected T proxy;

    protected String interfaceName;
}
