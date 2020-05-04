package hwang.chiuchieh.rpc.config;

import lombok.Data;

/**
 * @param <T> 需要引入的类的接口类型
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * 服务引入配置
 */
@Data
public class ReferenceConfig<T> {

    /**
     * Spring id（暂时忽略）
     */
    String id;

    /**
     * 引用的接口全限定名
     * 可为空，为空时为clazz的全限定名
     */
    String interfaceName;

    /**
     * 引用的接口类型
     * 【必填】
     */
    Class<T> clazz;

    /**
     * 路由规则, 暂时只支持前缀匹配、后缀匹配以及精确匹配
     * 如 [*.1.1]、[192.168.1.*]、[192.168.1.1]
     * 可为空
     */
    String routeRule;

    /**
     * 应用的配置
     * 【必填】
     */
    ApplicationConfig applicationConfig;

    /**
     * RPC协议的配置
     * 【必填】
     */
    ProtocolConfig protocolConfig;

    /**
     * 注册中心的配置
     * 【必填】
     */
    RegistryConfig registryConfig;

}
