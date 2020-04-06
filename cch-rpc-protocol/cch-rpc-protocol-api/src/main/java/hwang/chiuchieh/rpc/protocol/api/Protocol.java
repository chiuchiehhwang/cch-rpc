package hwang.chiuchieh.rpc.protocol.api;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.Invoker;
import hwang.chiuchieh.rpc.api.SPI;

/**
 * 协议接口
 * 进行服务的暴露与引入
 */
@SPI("cch")
public interface Protocol {
    void export(Invoker invoker, Info info);
}
