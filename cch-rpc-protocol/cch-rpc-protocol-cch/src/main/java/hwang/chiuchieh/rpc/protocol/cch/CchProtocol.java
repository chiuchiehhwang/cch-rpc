package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.Invoker;
import hwang.chiuchieh.rpc.protocol.api.AbstractProtocol;
import hwang.chiuchieh.rpc.remoting.netty.NettyServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CchProtocol extends AbstractProtocol {

    protected static Map<String, Invoker> invokerMap = new ConcurrentHashMap<>();

    @Override
    public void export(Invoker invoker, Info info) {
        NettyServer nettyServer = new NettyServer(info.getPort());
        nettyServer.openServer();
        Invoker existInvoker = invokerMap.get(((CchInvoker) invoker).getInterfaceName());
        if (existInvoker == null) {
            invokerMap.putIfAbsent(((CchInvoker)invoker).getInterfaceName(), invoker);
        }
    }
}
