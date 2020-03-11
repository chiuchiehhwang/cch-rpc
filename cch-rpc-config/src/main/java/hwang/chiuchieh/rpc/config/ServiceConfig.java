package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.test.api.Info;
import lombok.Data;

@Data
public class ServiceConfig {

    private String interfaceName;

    private String ref;

    public void export() {

        checkConfig();

        Info info = generateInfo();
    }

    private void checkConfig() {

    }

    private Info generateInfo() {
        return null;
    }

}
