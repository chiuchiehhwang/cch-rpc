package hwang.chiuchieh.rpc.remoting.netty;

import hwang.chiuchieh.rpc.remoting.api.Server;

public class NettyServer implements Server {

    private String host;

    private String port;

    public NettyServer(String host, String port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public boolean openServer() {
        return false;
    }
}
