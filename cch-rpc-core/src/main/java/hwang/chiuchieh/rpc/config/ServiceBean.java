package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.helper.ConfigHelper;
import hwang.chiuchieh.rpc.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Set;

@Slf4j
public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
//        export();
    }

    public void export() {
        ServiceFactory.export(getProvider());
    }

    public Provider<T> getProvider() {

        checkConfig();

        Provider<T> provider = new Provider<>();

        //设置协议相关配置
        provider.setPort(protocolConfig.getPort());
        provider.setProtocol(protocolConfig.getName());

        //设置服务相关配置
        provider.setInterfaceName(interfaceName);
        provider.setObj(ref);

        //设置注册中心相关配置
        Set<String> registries = ConfigHelper.getRegistries(registryConfig);
        provider.setRegistry(registryConfig.getName());
        provider.setRegistries(new ArrayList<>(registries));

        //设置应用名
        provider.setServiceName(applicationConfig.getName());

        return provider;
    }

    private void checkConfig() {
        if (StringUtils.isBlank(interfaceName)) {
            throw new CchRpcException("interface name is blank");
        }
        if (ref == null) {
            throw new CchRpcException("no instance of the class");
        }
        ConfigHelper.checkApplicationConfig(applicationConfig);
        ConfigHelper.checkProtocolConfig(protocolConfig);
        ConfigHelper.checkRegistryConfig(registryConfig);
    }
}
