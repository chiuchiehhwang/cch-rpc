package hwang.chiuchieh.rpc.config;

import lombok.Data;

/**
 * 注册中心配置
 */
@Data
public class RegistryConfig {

    public static String DEFAULT_REGISTRY = "zookeeper";

    /**
     * 注册中心协议名称，默认为zookeeper协议
     */
    private String name;

    /**
     * 注册中心主机ip
     */
    private String host;

    /**
     * 注册中心主机端口
     */
    private String port;

}
