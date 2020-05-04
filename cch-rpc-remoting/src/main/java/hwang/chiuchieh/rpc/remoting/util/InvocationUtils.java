package hwang.chiuchieh.rpc.remoting.util;

import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcRequestBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.RpcResponseBody;
import hwang.chiuchieh.rpc.remoting.cchprotocol.enums.FailType;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class InvocationUtils {


    private static Map<String, Provider> providerMap = new ConcurrentHashMap<>();

    private static Map<Long, BlockingQueue<Object>> invokeResult = new ConcurrentHashMap<>();

    public static void addProvider(Provider provider) {
        Provider existProvider = providerMap.get(provider.getInterfaceName());
        if (existProvider == null) {
            providerMap.putIfAbsent(provider.getInterfaceName(), provider);
        }
    }

    public static BlockingQueue<Object> getWaitQueue(long resultId) {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(1);
        invokeResult.put(resultId, queue);
        return queue;
    }

    public static void addResult(long resultId, Object result) {
        BlockingQueue<Object> queue = invokeResult.get(resultId);
        queue.offer(result);
    }

    public static RpcResponseBody invoke(RpcRequestBody body) {
        Provider provider = providerMap.get(body.getInterfaceName());
        if (provider == null) {
            return RpcUtils.getFailBody(FailType.NoProviderFind);
        }
        try {
            Class<?> instanceClass = Class.forName(body.getInterfaceName());
            Class<?>[] argsTypes = getArgsTypes(body.getArgsTypes());
            Method method = instanceClass.getMethod(body.getMethodName(), argsTypes);
            Object result = method.invoke(provider.getObj(), body.getArgsValues());
            return RpcUtils.getSuccessBody(result);
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }

    private static Class<?>[] getArgsTypes(String[] argsStrTypes) {
        if (argsStrTypes == null) {
            return new Class<?>[0];
        }
        Class<?>[] argsTypes = new Class<?>[argsStrTypes.length];
        try {
            for (int index = 0; index < argsStrTypes.length; index++) {
                argsTypes[index] = Class.forName(argsStrTypes[index]);
            }
            return argsTypes;
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }
}
