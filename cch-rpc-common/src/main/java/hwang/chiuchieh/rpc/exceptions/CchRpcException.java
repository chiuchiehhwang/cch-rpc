package hwang.chiuchieh.rpc.exceptions;

public class CchRpcException extends RuntimeException{

    public CchRpcException() {
        super();
    }

    public CchRpcException(String message) {
        super(message);
    }

    public CchRpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public CchRpcException(Throwable cause) {
        super(cause);
    }
}
