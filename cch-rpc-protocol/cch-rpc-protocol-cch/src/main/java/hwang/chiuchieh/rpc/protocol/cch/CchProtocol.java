package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.protocol.api.AbstractProtocol;
import hwang.chiuchieh.rpc.remoting.netty.NettyServer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CchProtocol extends AbstractProtocol {

    protected static Map<String, Provider> providerMap = new ConcurrentHashMap<>();

    @Override
    public void export(Provider provider, SPIExt spiExt) {
        NettyServer nettyServer = new NettyServer(provider.getPort());
        nettyServer.openServer();
        Provider existProvider = providerMap.get(provider.getInterfaceName());
        if (existProvider == null) {
            providerMap.putIfAbsent(provider.getInterfaceName(), provider);
        }
    }

    @Override
    public Object refer(Invoker invoker, List<RemoteInfo> remoteInfos, SPIExt spiExt) {
        return null;
    }
}
