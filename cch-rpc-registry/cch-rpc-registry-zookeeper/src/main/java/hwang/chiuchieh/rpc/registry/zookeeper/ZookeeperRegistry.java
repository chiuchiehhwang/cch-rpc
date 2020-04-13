package hwang.chiuchieh.rpc.registry.zookeeper;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.registry.api.Registry;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

public class ZookeeperRegistry implements Registry {

    private static final String PATH_SEPARATOR = "/";

    private static final String ADDRESS_SEPARATOR = ",";

    private static final String CCH_RPC_NAMESPACE = "cch-rpc";

    private volatile CuratorFramework client;

    @Override
    public void registry(Provider provider, SPIExt spiExt) {
        if(client == null) {
            synchronized (this) {
                if (client == null) {
                    buildAndStartClient(provider);
                }
            }
        }
        try {
            String path = PATH_SEPARATOR + provider.getInterfaceName()
                    + PATH_SEPARATOR + provider.getHost() + ":" + provider.getPort();
            client.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path);
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }

    @Override
    public List<RemoteInfo> getRemotes(Invoker invoker, SPIExt spiExt) {
        return null;
    }

    private void buildAndStartClient(Provider provider) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        boolean isStart = true;
        String address = "";
        List<String> registries = provider.getRegistries();
        for (String addr : registries) {
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
