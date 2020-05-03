package hwang.chiuchieh.rpc.remoting.util;

import hwang.chiuchieh.rpc.remoting.api.Serialization;
import hwang.chiuchieh.rpc.remoting.cchprotocol.Body;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.SerializationType;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.spi.SPIExt;

public class RpcUtils {

    /**
     * 代理工厂
     */
    private static Serialization SERIALIZATION =
            ExtensionLoader.getExtensionLoader(Serialization.class).getAdaptiveExtension();

    public static Body getBody(MsgType msgType, SerializationType sType, byte[] bodyByte) {
        SPIExt spiExt = new SPIExt();
        spiExt.put(SPIExt.SPI_SERIALIZATION, sType.name());

        return SERIALIZATION.deserialize(msgType, bodyByte, spiExt);
    }

    public static byte[] getBodyByte(MsgType msgType, SerializationType sType, Body body) {
        SPIExt spiExt = new SPIExt();
        spiExt.put(SPIExt.SPI_SERIALIZATION, sType.name());

        return SERIALIZATION.serialize(msgType, body, spiExt);

    }

}
