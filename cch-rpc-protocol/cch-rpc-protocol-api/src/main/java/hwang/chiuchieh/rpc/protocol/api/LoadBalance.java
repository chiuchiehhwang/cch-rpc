package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.spi.SPIExt;

public interface LoadBalance {

    RemoteInfo load(Invocation invocation, SPIExt spiExt);

}
