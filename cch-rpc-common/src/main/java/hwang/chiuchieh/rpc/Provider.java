package hwang.chiuchieh.rpc;

import hwang.chiuchieh.rpc.util.IPUtils;
import lombok.Data;

import java.util.List;

/**
 * @param <T> 服务实例类型
 * @author Chiuchieh Hwang
 * @date 2020/05/02
 * <p>
 * 服务导出配置
 */
@Data
public class Provider<T> {

    public Provider() {
        host = IPUtils.getLocalIP();
    }

    /**
     * 协议类型，如cch
     */
    private String protocol;

    /**
     * 服务地址
     */
    private String host;

    /**
     * 服务暴露端口
     */
    private String port;

    /**
     * 导出的服务
     */
    private String interfaceName;

    /**
     * 导出的服务类型
     */
    private Class<T> clazz;

    /**
     * 导出的服务实例
     */
    private T obj;

    /**
     * 序列化方式
     */
    private String serialization;

    /**
     * 传输方式
     */
    private String transport;

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
