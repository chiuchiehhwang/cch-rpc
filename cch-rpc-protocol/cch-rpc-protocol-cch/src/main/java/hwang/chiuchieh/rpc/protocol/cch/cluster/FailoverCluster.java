package hwang.chiuchieh.rpc.protocol.cch.cluster;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.protocol.api.CchProcessor;
import hwang.chiuchieh.rpc.spi.SPIExt;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FailoverCluster extends AbstractCluster {
    @Override
    public Object invoke(Invocation invocation, CchProcessor processor, SPIExt spiExt) {
        Invoker invoker = invocation.getInvoker();
        int retryCounts = invoker.getRetryCounts();
        CchRpcException exception = null;
        for (int count = 0; count < retryCounts + 1; count++) {
            List<RemoteInfo> remoteInfos = route(invocation, REGISTRY.getRemotes(invoker, spiExt));
            RemoteInfo remoteInfo = LOAD_BALANCE.load(invocation, remoteInfos, spiExt);
            invocation.setHost(remoteInfo.getHost());
            invocation.setPort(remoteInfo.getPort());
            try {
                Object result = processor.invokeNext(processor, invocation);
                return result;
            } catch (Throwable e) {
                exception = new CchRpcException(e);
                log.error("Failover调用发生异常", e);
            }
        }
        throw exception;
    }

}
