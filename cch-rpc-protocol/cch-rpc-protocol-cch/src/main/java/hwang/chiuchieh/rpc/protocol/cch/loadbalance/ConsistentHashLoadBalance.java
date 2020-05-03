package hwang.chiuchieh.rpc.protocol.cch.loadbalance;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.protocol.api.LoadBalance;
import hwang.chiuchieh.rpc.spi.SPIExt;

public class ConsistentHashLoadBalance implements LoadBalance {
    @Override
    public RemoteInfo load(Invocation invocation, SPIExt spiExt) {
        return null;
    }
}
