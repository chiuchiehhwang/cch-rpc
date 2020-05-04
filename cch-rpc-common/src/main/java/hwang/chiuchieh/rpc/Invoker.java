package hwang.chiuchieh.rpc;

import lombok.Data;

import java.util.List;

/**
 * @param <T>
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * 封装具体的代理类及相关信息
 * 并实现具体的调用功能
 */
@Data
public class Invoker<T> {

    /**
     * 协议类型，如cch
     */
    private String protocol;

    /**
     * 引入的服务接口全限定名
     */
    private String interfaceName;

    /**
     * 引入的服务类型
     */
    private Class<T> clazz;

    /**
     * 代理
     */
    private String proxy;

    /**
     * 序列化方式
     */
    private String serialization;

    /**
     * 传输方式
     */
    private String transport;

    /**
     * 容错策略
     */
    private String cluster;

    /**
     * 负载均衡策略
     */
    private String loadBalance;

    /**
     * 路由规则, 暂时只支持前缀匹配、后缀匹配以及精确匹配
     * 如
     * [*.1.1]、[192.168.1.*]、[192.168.1.1]
     */
    private String routeRule;

    /**
     * failover策略的重试次数
     */
    private int retryCounts;

    /**
     * 注册中心类型，如zookeeper
     */
    private String registry;

    /**
     * 注册中心地址, host:port
     */
    private List<String> registries;

    /**
     * 应用名
     */
    private String serviceName;
}
