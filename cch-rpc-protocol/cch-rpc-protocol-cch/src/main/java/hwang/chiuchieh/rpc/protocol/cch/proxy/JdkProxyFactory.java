package hwang.chiuchieh.rpc.protocol.cch.proxy;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.protocol.api.ProxyFactory;
import hwang.chiuchieh.rpc.protocol.cch.CallHandler;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyFactory implements ProxyFactory {


    @Override
    public <T> T getProxy(Invoker<T> invoker, SPIExt spiExt) {
        CchInvocationHandler<T> handler = new CchInvocationHandler<>(invoker);
        Object serverStub = Proxy.newProxyInstance(invoker.getClazz().getClassLoader(),
                invoker.getClazz().getInterfaces(), handler);

        return (T) serverStub;
    }

    private static class CchInvocationHandler<T> implements InvocationHandler {

        private CallHandler callHandler = new CallHandler();

        private Invoker<T> invoker;

        public CchInvocationHandler(Invoker<T> invoker) {
            this.invoker = invoker;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return callHandler.call(invoker, method, args);
        }
    }

}
