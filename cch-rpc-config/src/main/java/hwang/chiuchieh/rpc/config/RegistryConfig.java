package hwang.chiuchieh.rpc.config;

import lombok.Data;

/**
 * @author Chiuchieh Hwang
 *
 * 注册中心配置
 * 只支持单注册中心
 * 如果注册中心地址只有一个，那么可以配置host、port或者是registries；
 * 如果注册中心有多个，则将会取host、port和registries配置的并集。
 */
@Data
public class RegistryConfig {

    public static String DEFAULT_REGISTRY = "zookeeper";

    public static String HOST_NAME_SEPARATOR = ":";

    public static String ADDRESS_SEPARATOR = ",";

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

    /**
     * 注册中心List，如果配置多个地址则需要使用该参数配置注册中心地址。
     * address中的地址使用英文逗号分隔，和host:port的配置取并集。
     * 配置示例：
     * 192.168.0.3:8888,192.168.0.4:8888
     */
    private String addresses;

}
