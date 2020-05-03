package hwang.chiuchieh.rpc.config;


import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.helper.ConfigHelper;
import hwang.chiuchieh.rpc.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Set;

public class ReferenceBean<T> extends ReferenceConfig<T> implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
//        refer();
    }

    public T refer() {
        return ServiceFactory.refer(getInvoker());
    }

    public Invoker<T> getInvoker() {

        checkConfig();

        Invoker<T> invoker = new Invoker<>();

        //设置协议类型
        invoker.setProtocol(protocolConfig.getName());

        //设置引入的服务
        invoker.setInterfaceName(interfaceName);
        invoker.setClazz(clazz);

        //设置注册中心相关配置
        Set<String> registries = ConfigHelper.getRegistries(registryConfig);
        invoker.setRegistry(registryConfig.getName());
        invoker.setRegistries(new ArrayList<>(registries));

        //设置应用名
        invoker.setServiceName(applicationConfig.getName());

        return invoker;
    }

    private void checkConfig() {
        if (StringUtils.isBlank(interfaceName)) {
            throw new CchRpcException("interface name is blank");
        }
        ConfigHelper.checkApplicationConfig(applicationConfig);
        ConfigHelper.checkProtocolConfig(protocolConfig);
        ConfigHelper.checkRegistryConfig(registryConfig);
    }
}
