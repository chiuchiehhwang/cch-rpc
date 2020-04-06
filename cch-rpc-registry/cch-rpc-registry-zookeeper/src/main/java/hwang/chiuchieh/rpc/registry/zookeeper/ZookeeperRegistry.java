package hwang.chiuchieh.rpc.registry.zookeeper;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.registry.api.Registry;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class ZookeeperRegistry implements Registry {

    private static final String PATH_SEPARATOR = "/";

    private static final String ADDRESS_SEPARATOR = ",";

    private static final String CCH_RPC_NAMESPACE = "cch-rpc";

    private volatile CuratorFramework client;




    @Override
    public void registry(Info info) {
        if(client == null) {
            synchronized (this) {
                if (client == null) {
                    buildAndStartClient(info);
                }
            }
        }
        try {
            String path = PATH_SEPARATOR + info.getServiceName()
                    + PATH_SEPARATOR + info.getHost() + ":" + info.getPort();
            client.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path);
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }

    private void buildAndStartClient(Info info) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        boolean isStart = true;
        String address = "";
        for (String addr : info.getRegistries()) {
            if (isStart) {
                address = address + addr;
                isStart = false;
                continue;
            }
            address = address + ADDRESS_SEPARATOR + addr;
        }
        client = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(60000)
                .connectionTimeoutMs(15000)
                .retryPolicy(retryPolicy)
                .namespace(CCH_RPC_NAMESPACE)
                .build();
        client.start();
    }
}
