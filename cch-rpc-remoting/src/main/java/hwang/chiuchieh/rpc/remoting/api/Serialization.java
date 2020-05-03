package hwang.chiuchieh.rpc.remoting.api;

import hwang.chiuchieh.rpc.remoting.cchprotocol.Body;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.MsgType;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

@SPI("gson")
public interface Serialization {

    Body deserialize(MsgType msgType, byte[] bodyByte, SPIExt spiExt);

    byte[] serialize(MsgType msgType, Body body, SPIExt spiExt);

}
