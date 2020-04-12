package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.protocol.api.ProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CchProxyFactory implements ProxyFactory {


    @Override
    public <T> Invoker<T> getProxy(String interfaceName, T instance, SPIExt spiExt) {
        CchInvocationHandler handler = new CchInvocationHandler(instance);
        T serverStub = (T) Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), handler);

        Invoker<T> invoker = new Invoker<>();
        invoker.setInterfaceName(interfaceName);
        invoker.setProxy(serverStub);

        return invoker;
    }

    class CchInvocationHandler implements InvocationHandler{

        private Object subject;

        public CchInvocationHandler(Object subject) {
            this.subject = subject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(subject, args);
        }
    }

}
