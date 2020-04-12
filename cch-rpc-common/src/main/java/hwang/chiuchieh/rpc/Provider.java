package hwang.chiuchieh.rpc;

import lombok.Data;

import java.util.List;

/**
 * @author Chiuchieh Hwang
 *
 * @param <T>
 */
@Data
public class Provider<T> {

    public final static String PATH_SEPARATOR = "/";

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

    protected T obj;

    protected String interfaceName;
}
