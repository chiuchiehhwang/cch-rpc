package hwang.chiuchieh.rpc.exceptions;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * CchRpc异常类
 */
public class CchRpcException extends RuntimeException {

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
