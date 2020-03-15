package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.Invoker;
import hwang.chiuchieh.rpc.api.SPI;

@SPI("cch")
public interface Protocol {
    void export(Invoker invoker, Info info);
}
