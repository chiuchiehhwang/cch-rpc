package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.helper.ConfigHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.Set;

/**
 * @param <T> 导出类类型
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * 配置类服务导出出口
 */
@Slf4j
public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
//        export();
    }

    public void export() {
        ServiceFactory.export(getProvider());
    }

    private Provider<T> getProvider() {

        checkConfig();

        Provider<T> provider = new Provider<>();

        //设置协议相关配置
        provider.setProtocol(protocolConfig.getName());
        provider.setPort(protocolConfig.getPort());
        provider.setSerialization(protocolConfig.getSerialization());
        provider.setTransport(protocolConfig.getTransport());

        //设置服务相关配置
        if (interfaceName == null) {
            interfaceName = clazz.getName();
        }
        provider.setClazz(clazz);
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
        if (clazz == null) {
            throw new CchRpcException("clazz cannot be empty");
        }
        if (ref == null) {
            throw new CchRpcException("no instance of the class");
        }
        ConfigHelper.checkApplicationConfig(applicationConfig);
        ConfigHelper.checkProtocolConfig(protocolConfig);
        ConfigHelper.checkRegistryConfig(registryConfig);
    }
}
