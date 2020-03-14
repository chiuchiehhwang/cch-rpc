package hwang.chiuchieh.rpc.config;

import lombok.Data;

/**
 * 协议配置
 */
@Data
public class ProtocolConfig {

    public static String DEFAULT_PROTOCOL = "cch";

    /**
     * 协议名称，默认为cch协议
     */
    private String name;

}
