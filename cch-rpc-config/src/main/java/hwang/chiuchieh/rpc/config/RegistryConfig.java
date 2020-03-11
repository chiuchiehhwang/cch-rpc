package hwang.chiuchieh.rpc.config;

import lombok.Data;

@Data
public class RegistryConfig {

    private static String DEFAULT_REGISTRY = "zookeeper";

    private String name = DEFAULT_REGISTRY;

    private String host;

    private String port;

}
