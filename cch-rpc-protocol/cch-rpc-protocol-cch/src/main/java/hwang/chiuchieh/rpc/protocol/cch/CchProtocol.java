package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.Invoker;
import hwang.chiuchieh.rpc.protocol.api.Protocol;
import hwang.chiuchieh.rpc.remoting.netty.NettyServer;


public class CchProtocol implements Protocol {

    @Override
    public void export(Invoker invoker, Info info) {
        NettyServer nettyServer = new NettyServer(info.getPort());
        nettyServer.openServer();
    }
}
