package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.util.CollectionUtils;
import hwang.chiuchieh.rpc.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
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
        String path = host + Provider.PATH_SEPARATOR + port + Provider.PATH_SEPARATOR + interfaceName;

        Set<String> registries = getRegistries();

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
        checkApplicationConfig();
        checkProtocolConfig();
        checkRegistryConfig();
    }

    private void checkApplicationConfig() {
        if(applicationConfig == null) {
            throw new CchRpcException("no application's configuration");
        }
        if(StringUtils.isBlank(applicationConfig.getName())) {
            throw new CchRpcException("application name is blank");
        }
    }

    private void checkProtocolConfig() {
        if(protocolConfig == null) {
            protocolConfig = new ProtocolConfig();
        }
        if(StringUtils.isBlank(protocolConfig.getName())) {
            protocolConfig.setName(ProtocolConfig.DEFAULT_PROTOCOL);
        }
        if(StringUtils.isBlank(protocolConfig.getPort())) {
            protocolConfig.setPort(ProtocolConfig.DEFAULT_PROTOCOL_PORT);
        } else {
            try {
                int port = Integer.valueOf(protocolConfig.getPort());
                if (port <= 1023) {
                    throw new CchRpcException("port < 1024");
                }
            } catch (NumberFormatException e) {
                throw new CchRpcException("port is invalid");
            }
        }
    }

    private void checkRegistryConfig() {
        if(registryConfig == null) {
            throw new CchRpcException("no registry's configuration");
        }
        if(StringUtils.isBlank(registryConfig.getName())) {
            registryConfig.setName(RegistryConfig.DEFAULT_REGISTRY);
        }
        Set<String> registries = getRegistries();
        if (CollectionUtils.isEmpty(registries)) {
            throw new CchRpcException("no registry's address");
        }
    }



    private SPIExt getSPIExt() {
        SPIExt spiExt = new SPIExt();
        //填充SPI路由信息
        spiExt.put(SPIExt.SPI_PROTOCOL, protocolConfig.getName());
        spiExt.put(SPIExt.SPI_PROXY_FACTORY, protocolConfig.getName());
        spiExt.put(SPIExt.SPI_REGISTRY, registryConfig.getName());
        return spiExt;
    }

    private Set<String> getRegistries() {
        Set<String> registries = new HashSet<>();
        if (registryConfig == null) {
            return registries;
        }
        if (StringUtils.isNotBlank(registryConfig.getHost())
                && StringUtils.isNotBlank(registryConfig.getPort())) {
            registries.add(registryConfig.getHost()
                    + RegistryConfig.HOST_NAME_SEPARATOR
                    + registryConfig.getPort());
        }
        String addresses = registryConfig.getAddresses();
        if (StringUtils.isNotBlank(addresses)) {
            String[] addrArray = addresses.split(RegistryConfig.ADDRESS_SEPARATOR);
            for (String addr : addrArray) {
                if (StringUtils.isNotBlank(addr)) {
                    registries.add(addr);
                }
            }
        }
        return registries;
    }
}
