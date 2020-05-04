package hwang.chiuchieh.rpc.spi;

import java.util.HashMap;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * SPI实现类配置信息
 */
public class SPIExt extends HashMap<String, String> {

    /**
     * RPC协议
     */
    public final static String SPI_PROTOCOL = "extensionProtocol";

    /**
     * 注册中心
     */
    public final static String SPI_REGISTRY = "extensionRegistry";

    /**
     * 代理类型
     */
    public final static String SPI_PROXY_FACTORY = "extensionProxyFactory";

    /**
     * 负载均衡策略
     */
    public final static String SPI_LOAD_BALANCE = "extensionLoadBalance";

    /**
     * 容错策略
     */
    public final static String SPI_CLUSTER = "extensionCluster";

    /**
     * 序列化类型
     */
    public final static String SPI_SERIALIZATION = "extensionSerialization";

    /**
     * 客户端传输类型
     */
    public final static String SPI_CLIENT = "extensionClient";

    /**
     * 服务端传输类型
     */
    public final static String SPI_SERVER = "extensionServer";

}
