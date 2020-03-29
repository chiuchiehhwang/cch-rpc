package hwang.chiuchieh.rpc.api;

import lombok.Data;

import java.util.HashMap;

@Data
public class Info extends HashMap<String, String>{

    public final static String SPI_PROTOCOL = "extensionProtocol";
    public final static String SPI_REGISTRY = "extensionRegistry";
    public final static String SPI_SERIALIZATION = "extensionSerialization";
    public final static String SPI_TRANSPORT = "extensionTransport";

    public final static String PATH_SEPARATOR = "/";

    private String host;

    private String port;

    /**
     * host/port/serviceName
     */
    private String path;

//    public String generateRegistryUrl() {
//        return null;
//    }
//
//    public String generateServiceUrl() {
//        return null;
//    }


}
