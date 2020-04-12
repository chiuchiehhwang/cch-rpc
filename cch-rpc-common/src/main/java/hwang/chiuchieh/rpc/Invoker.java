package hwang.chiuchieh.rpc;

import lombok.Data;

/**
 * 封装具体的代理类及相关信息
 * 并实现具体的调用功能
 *
 * @param <T>
 */
@Data
public class Invoker<T> {

    protected T proxy;

    protected String interfaceName;
}
