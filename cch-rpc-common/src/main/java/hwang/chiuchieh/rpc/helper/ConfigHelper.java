package hwang.chiuchieh.rpc.helper;

import hwang.chiuchieh.rpc.config.ApplicationConfig;
import hwang.chiuchieh.rpc.config.ProtocolConfig;
import hwang.chiuchieh.rpc.config.RegistryConfig;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.util.CollectionUtils;
import hwang.chiuchieh.rpc.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class ConfigHelper {

    public static void checkApplicationConfig(ApplicationConfig applicationConfig) {
        if (applicationConfig == null) {
            throw new CchRpcException("no application's configuration");
        }
        if (StringUtils.isBlank(applicationConfig.getName())) {
            throw new CchRpcException("application name is blank");
        }
    }

    public static void checkProtocolConfig(ProtocolConfig protocolConfig) {
        if (protocolConfig == null) {
            throw new CchRpcException("no protocol's configuration");
        }
        if (StringUtils.isBlank(protocolConfig.getName())) {
            protocolConfig.setName(ProtocolConfig.DEFAULT_PROTOCOL);
        }
        if (StringUtils.isBlank(protocolConfig.getPort())) {
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

    public static void checkRegistryConfig(RegistryConfig registryConfig) {
        if (registryConfig == null) {
            throw new CchRpcException("no registry's configuration");
        }
        if (StringUtils.isBlank(registryConfig.getName())) {
            registryConfig.setName(RegistryConfig.DEFAULT_REGISTRY);
        }
        Set<String> registries = getRegistries(registryConfig);
        if (CollectionUtils.isEmpty(registries)) {
            throw new CchRpcException("no registry's address");
        }
    }

    public static Set<String> getRegistries(RegistryConfig registryConfig) {
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
