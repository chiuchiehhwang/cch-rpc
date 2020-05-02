package hwang.chiuchieh.rpc.config;

import lombok.Data;

/**
 * 应用信息配置
 */
@Data
public class ApplicationConfig {

    /**
     * 应用名称，作为应用的唯一标识, 必填
     */
    private String name;

}
