package hwang.chiuchieh.rpc.config;


import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.helper.ConfigHelper;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Set;

/**
 * @param <T> 引入类类型
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * 配置类服务引入入口
 */
public class ReferenceBean<T> extends ReferenceConfig<T> implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
//        refer();
    }

    public T refer() {
        return ServiceFactory.refer(getInvoker());
    }

    private Invoker<T> getInvoker() {

        checkConfig();

        Invoker<T> invoker = new Invoker<>();

        //设置协议类型
        invoker.setProtocol(protocolConfig.getName());
        invoker.setProxy(protocolConfig.getProxy());
        invoker.setSerialization(protocolConfig.getSerialization());
        invoker.setTransport(protocolConfig.getTransport());
        invoker.setCluster(protocolConfig.getCluster());
        invoker.setRetryCounts(protocolConfig.getRetryCounts());
        invoker.setLoadBalance(protocolConfig.getLoadBalance());

        //设置引入的服务
        if (interfaceName == null) {
            interfaceName = clazz.getName();
        }
        invoker.setClazz(clazz);
        invoker.setInterfaceName(interfaceName);
        invoker.setRouteRule(routeRule);


        //设置注册中心相关配置
        Set<String> registries = ConfigHelper.getRegistries(registryConfig);
        invoker.setRegistry(registryConfig.getName());
        invoker.setRegistries(new ArrayList<>(registries));

        //设置应用名
        invoker.setServiceName(applicationConfig.getName());

        return invoker;
    }

    private void checkConfig() {
        if (clazz == null) {
            throw new CchRpcException("clazz cannot be empty");
        }
        ConfigHelper.checkApplicationConfig(applicationConfig);
        ConfigHelper.checkProtocolConfig(protocolConfig);
        ConfigHelper.checkRegistryConfig(registryConfig);
    }
}
