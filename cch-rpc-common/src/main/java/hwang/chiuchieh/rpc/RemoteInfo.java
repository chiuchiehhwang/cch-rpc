package hwang.chiuchieh.rpc;

import lombok.Data;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * 远程机器的地址端口信息
 */
@Data
public class RemoteInfo {

    /**
     * ip地址
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    @Override
    public String toString() {
        return host + ":" + port;
    }

}
