package hwang.chiuchieh.rpc.protocol.cch;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.protocol.cch.filter.CallProcessor;
import hwang.chiuchieh.rpc.protocol.api.CchProcessor;
import hwang.chiuchieh.rpc.protocol.cch.filter.ClusterProcessor;

import java.lang.reflect.Method;
import java.util.List;

public class CallHandler {

    public Object call(Invoker invoker, Method method, Object[] args) {
        Invocation invocation = buildInvocation(invoker, method, args);
        CchProcessor processor = init();
        return processor.process(invocation);
    }

    private Invocation buildInvocation(Invoker invoker, Method method, Object[] args) {
        Invocation invocation = new Invocation();
        invocation.setInvoker(invoker);
        invocation.setMethodName(method.getName());
        invocation.setArgsTypes(method.getParameterTypes());
        invocation.setReturnType(method.getReturnType());
        invocation.setArgs(args);
        return invocation;
    }

    private CchProcessor init() {
        List<CchProcessor> processors = CchProcessor.getProcessors();
        processors.add(new ClusterProcessor());
        processors.add(new CallProcessor());
        return processors.get(0);
    }

}
