package hwang.chiuchieh.rpc.protocol.cch.filter;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.remoting.netty.NettyClient;

public class CallProcessor extends CchProcessor {

    public Object process(Invocation invocation) {
        NettyClient client = new NettyClient();
        return client.invoke(invocation);
    }

}
