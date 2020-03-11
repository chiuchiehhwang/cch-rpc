package hwang.chiuchieh.rpc.config;

import org.springframework.beans.factory.InitializingBean;

public class ServiceBean extends ServiceConfig implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        export();
    }
}
