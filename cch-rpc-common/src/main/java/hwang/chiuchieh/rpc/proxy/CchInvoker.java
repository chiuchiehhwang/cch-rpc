package hwang.chiuchieh.rpc.proxy;

import hwang.chiuchieh.rpc.api.Invoker;
import lombok.Data;

@Data
public class CchInvoker<T> implements Invoker<T> {

    private T proxy;

    private String interfaceName;

    private String url;

}
