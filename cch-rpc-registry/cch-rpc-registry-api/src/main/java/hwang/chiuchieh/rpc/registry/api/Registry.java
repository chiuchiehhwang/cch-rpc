package hwang.chiuchieh.rpc.registry.api;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.SPI;

@SPI("zookeeper")
public interface Registry {
    void registry(Info info);
}
