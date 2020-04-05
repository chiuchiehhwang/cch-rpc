package hwang.chiuchieh.rpc.registry.zookeeper;

import hwang.chiuchieh.rpc.api.Info;
import hwang.chiuchieh.rpc.api.SPI;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.registry.api.Registry;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class ZookeeperRegistry implements Registry {

    private static final String PATH_SEPARATOR = "/";

    CuratorFramework client;

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    @Override
    public void registry(Info info) {
        client = CuratorFrameworkFactory.builder()
                .connectString(info.getRegistries().get(0))
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("cch-rpc")
                .build();
        client.start();
        try {
            client.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(PATH_SEPARATOR + info.getServiceName() + PATH_SEPARATOR + info.getHost() + ":" + info.getPort());
        } catch (Exception e) {
            throw new CchRpcException(e);
        }

    }
}
