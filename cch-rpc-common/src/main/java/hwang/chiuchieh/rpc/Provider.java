package hwang.chiuchieh.rpc;

import hwang.chiuchieh.rpc.config.ProtocolConfig;
import hwang.chiuchieh.rpc.config.RegistryConfig;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
        try {
            //获取本地IP
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new CchRpcException("can't get local address", e);
        }
    }

    /**
     * 协议类型，如cch
     */
    private String protocol = ProtocolConfig.DEFAULT_PROTOCOL;

    /**
     * 服务地址
     */
    private String host;

    /**
     * 服务暴露端口
     */
    private String port = ProtocolConfig.DEFAULT_PROTOCOL_PORT;

    /**
     * 导出的服务
     */
    private String interfaceName;

    /**
     * 导出的服务实例
     */
    private T obj;

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
