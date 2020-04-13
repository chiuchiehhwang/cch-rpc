package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.helper.ConfigHelper;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceFactory.export(getProvider());
    }

    public void export() {
        ServiceFactory.export(getProvider());
    }

    public Provider<T> getProvider() {

        checkConfig();

        Provider<T> provider = new Provider<>();
        provider.setInterfaceName(interfaceName);
        provider.setObj(ref);

        String host;
        try {
            //获取本地IP
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new CchRpcException("can't get local address", e);
        }
        String port = protocolConfig.getPort();

        Set<String> registries = ConfigHelper.getRegistries(registryConfig);

        provider.setHost(host);
        provider.setPort(port);
        provider.setServiceName(interfaceName);
        provider.setRegistries(new ArrayList<>(registries));

        return provider;
    }

    private void checkConfig() {
        if(StringUtils.isBlank(interfaceName)) {
            throw new CchRpcException("interface name is blank");
        }
        if(ref == null) {
            throw new CchRpcException("no instance of the class");
        }
        ConfigHelper.checkApplicationConfig(applicationConfig);
        ConfigHelper.checkProtocolConfig(protocolConfig);
        ConfigHelper.checkRegistryConfig(registryConfig);
    }
}
