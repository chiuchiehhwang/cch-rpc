package hwang.chiuchieh.rpc.config;

import lombok.Data;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * 协议配置
 */
@Data
public class ProtocolConfig {

    public static final String DEFAULT_PROTOCOL_PORT = "2333";

    public static final int DEFAULT_FAILOVER_RETRY_COUNTS = 1;

    /**
     * 协议名称
     * 可为空，默认为cch协议
     */
    private String name;

    /**
     * 服务暴露端口
     * 可为空，需大于等于1024，cch协议中默认为2333
     */
    private String port = DEFAULT_PROTOCOL_PORT;

    /**
     * 代理
     * 可为空，cch协议中默认为jdk
     */
    private String proxy;

    /**
     * 序列化方式
     * 可为空，cch协议中默认为gson
     */
    private String serialization;

    /**
     * 传输方式
     * 可为空，cch协议中默认为netty
     */
    private String transport;

    /**
     * 容错策略
     * 可为空，cch协议中默认为failover
     */
    private String cluster;

    /**
     * failover策略失败重试次数
     * 可为空，默认为1
     */
    private int retryCounts = DEFAULT_FAILOVER_RETRY_COUNTS;

    /**
     * 负载均衡策略
     * 可为空，cch协议中默认为consistentHash
     */
    private String loadBalance;

}
