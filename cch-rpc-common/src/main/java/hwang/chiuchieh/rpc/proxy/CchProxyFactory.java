package hwang.chiuchieh.rpc.proxy;

import hwang.chiuchieh.rpc.api.Invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CchProxyFactory implements ProxyFactory {
    @Override
    public <T> Invoker<T> getInvoker(String interfaceName, T instance) {
        CchInvocationHandler handler = new CchInvocationHandler(instance);
        T serverStub = (T) Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(), handler);

        CchInvoker<T> invoker = new CchInvoker<>();
        invoker.setInterfaceName(interfaceName);
        invoker.setProxy(serverStub);
        invoker.setUrl(null);

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
