package hwang.chiuchieh.rpc.test.api;

import lombok.Data;

import java.util.HashMap;

@Data
public class Info extends HashMap<String, String>{

    public static String SPI_PROTOCOL = "extensionProtocol";
    public static String SPI_REGISTRY = "extensionRegistry";
    public static String SPI_SERIALIZATION = "extensionSerialization";
    public static String SPI_TRANSPORT = "extensionTransport";

    private String host;

    private String port;

    private String path;

//    public String generateRegistryUrl() {
//        return null;
//    }
//
//    public String generateServiceUrl() {
//        return null;
//    }


}
