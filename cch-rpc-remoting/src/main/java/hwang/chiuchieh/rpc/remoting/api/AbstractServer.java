package hwang.chiuchieh.rpc.remoting.api;

public abstract class AbstractServer implements Server {

    protected String host;

    protected String port;

    public AbstractServer(String host, String port) {
        this.host = host;
        this.port = port;
    }

}
