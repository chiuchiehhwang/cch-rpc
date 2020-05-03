package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.util.List;

@SPI("consistentHash")
public interface LoadBalance {

    RemoteInfo load(Invocation invocation, List<RemoteInfo> remoteInfos, SPIExt spiExt);

}
