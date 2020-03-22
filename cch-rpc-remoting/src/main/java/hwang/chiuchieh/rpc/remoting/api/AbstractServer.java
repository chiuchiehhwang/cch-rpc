package hwang.chiuchieh.rpc.remoting.api;

public abstract class AbstractServer implements Server {

    protected String port;

    public AbstractServer(String port) {
        this.port = port;
    }

}
