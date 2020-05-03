package hwang.chiuchieh.rpc.protocol.cch.processor;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.protocol.api.CchProcessor;
import hwang.chiuchieh.rpc.remoting.netty.NettyClient;

public class CallProcessor extends CchProcessor {

    public Object process(Invocation invocation) {
        NettyClient client = new NettyClient();
        return client.invoke(invocation);
    }

}
