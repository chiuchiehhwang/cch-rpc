package hwang.chiuchieh.rpc.helper;

import hwang.chiuchieh.rpc.config.ApplicationConfig;
import hwang.chiuchieh.rpc.config.ProtocolConfig;
import hwang.chiuchieh.rpc.config.RegistryConfig;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.util.CollectionUtils;
import hwang.chiuchieh.rpc.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * 配置信息Helper类
 */
public class ConfigHelper {

    /**
     * 检查应用信息是否合法
     *
     * @param applicationConfig 应用信息配置
     */
    public static void checkApplicationConfig(ApplicationConfig applicationConfig) {
        if (applicationConfig == null) {
            throw new CchRpcException("no application configuration");
        }
        if (StringUtils.isEmpty(applicationConfig.getName())) {
            throw new CchRpcException("app name cannot be empty");
        }
    }

    /**
     * 检查协议配置是否合法
     *
     * @param protocolConfig 协议配置
     */
    public static void checkProtocolConfig(ProtocolConfig protocolConfig) {
        if (protocolConfig == null) {
            throw new CchRpcException("no protocol configuration");
        }
        if (StringUtils.isEmpty(protocolConfig.getPort())) {
            protocolConfig.setPort(ProtocolConfig.DEFAULT_PROTOCOL_PORT);
        } else {
            try {
                int port = Integer.valueOf(protocolConfig.getPort());
                if (port <= 1023) {
                    throw new CchRpcException("port needs to be greater than or equal to 1024");
                }
            } catch (NumberFormatException e) {
                throw new CchRpcException("illegal port", e);
            }
        }
    }

    /**
     * 检查注册中心配置是否合法
     *
     * @param registryConfig 注册中心配置
     */
    public static void checkRegistryConfig(RegistryConfig registryConfig) {
        if (registryConfig == null) {
            throw new CchRpcException("no registry configuration");
        }
        Set<String> registries = getRegistries(registryConfig);
        if (CollectionUtils.isEmpty(registries)) {
            throw new CchRpcException("no registry address");
        }
    }

    /**
     * 从注册中心配置中获取到注册中心的全部地址
     *
     * @param registryConfig 注册中心配置
     * @return 注册中心地址列表
     */
    public static Set<String> getRegistries(RegistryConfig registryConfig) {
        Set<String> registries = new HashSet<>();
        if (registryConfig == null) {
            return registries;
        }
        if (StringUtils.isNotEmpty(registryConfig.getHost())
                && StringUtils.isNotEmpty(registryConfig.getPort())) {
            registries.add(registryConfig.getHost()
                    + RegistryConfig.HOST_NAME_SEPARATOR
                    + registryConfig.getPort());
        }
        String addresses = registryConfig.getAddresses();
        if (StringUtils.isNotEmpty(addresses)) {
            String[] addrArray = addresses.split(RegistryConfig.ADDRESS_SEPARATOR);
            for (String addr : addrArray) {
                if (StringUtils.isNotEmpty(addr) && addr.contains(RegistryConfig.HOST_NAME_SEPARATOR)) {
                    registries.add(addr);
                }
            }
        }
        return registries;
    }
}
