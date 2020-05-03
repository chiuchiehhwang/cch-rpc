package hwang.chiuchieh.rpc;

import lombok.Data;

@Data
public class RemoteInfo {

    private String host;

    private Integer port;

    @Override
    public String toString() {
        return host + ":" + port;
    }

}
