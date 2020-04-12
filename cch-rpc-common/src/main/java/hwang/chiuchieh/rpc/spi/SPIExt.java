package hwang.chiuchieh.rpc.spi;

import java.util.HashMap;

public class SPIExt extends HashMap<String, String> {

    public final static String SPI_PROTOCOL = "extensionProtocol";
    public final static String SPI_REGISTRY = "extensionRegistry";
    public final static String SPI_PROXY_FACTORY = "extensionProxyFactory";
    public final static String SPI_SERIALIZATION = "extensionSerialization";
    public final static String SPI_TRANSPORT = "extensionTransport";

}
