package hwang.chiuchieh.rpc.registry.zookeeper;

import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.Provider;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.registry.api.Registry;
import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.util.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZookeeperRegistry implements Registry {

    private static final String PATH_SEPARATOR = "/";

    private static final String PROVIDER_PATH = "providers";

    private static final String INVOKER_PATH = "invokers";

    private static final String ADDRESS_SEPARATOR = ",";

    private static final String CCH_RPC_NAMESPACE = "cch-rpc";

    private volatile CuratorFramework client;

    private static Map<String, List<RemoteInfo>> catalogCache = new ConcurrentHashMap<>();

    @Override
    public <T> void registry(Provider<T> provider, SPIExt spiExt) {
        try {
            checkAndInitClient(provider.getRegistries());
            String path = PATH_SEPARATOR + provider.getInterfaceName() + PATH_SEPARATOR + PROVIDER_PATH
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
    public <T> List<RemoteInfo> getRemotes(Invoker<T> invoker, SPIExt spiExt) {
        checkAndInitClient(invoker.getRegistries());
        String providerPath = PATH_SEPARATOR + invoker.getInterfaceName() + PATH_SEPARATOR + PROVIDER_PATH;
//        String invokerPath = PATH_SEPARATOR + invoker.getInterfaceName() + PATH_SEPARATOR + PROVIDER_PATH
//                + PATH_SEPARATOR + invoker.getHost() + ":" + invoker.getPort();

        //从本地缓存中读取
        if (catalogCache.get(providerPath) != null) {
            return catalogCache.get(providerPath);
        }
        List<RemoteInfo> remoteInfos = getRemoteInfos(providerPath);
        catalogCache.putIfAbsent(providerPath, remoteInfos);
        return catalogCache.get(providerPath);
    }

    private void checkAndInitClient(List<String> registries) {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    buildAndStartClient(registries);
                }
            }
        }
    }

    private void buildAndStartClient(List<String> registries) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        boolean isStart = true;
        String address = "";
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

    private void addListener(String providerPath) {
        try {
            PathChildrenCache cache = new PathChildrenCache(client, providerPath, true);
            cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            cache.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                    switch (pathChildrenCacheEvent.getType()) {
                        case CHILD_ADDED:
                            handleUpdate(providerPath);
                            break;
                        case CHILD_REMOVED:
                            handleUpdate(providerPath);
                            break;
                    }
                }
            });
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }

    private void handleUpdate(String providerPath) {
        List<RemoteInfo> remoteInfos = getRemoteInfos(providerPath);
        catalogCache.put(providerPath, remoteInfos);
    }

    private List<RemoteInfo> getRemoteInfos(String providerPath) {
        try {
            List<RemoteInfo> remoteInfos = new ArrayList<>();
            List<String> addresses = client.getChildren().forPath(providerPath);
            for (String addr : addresses) {
                if (StringUtils.isBlank(addr)) {
                    continue;
                }
                String[] hostAndPort = addr.split(":");
                if (hostAndPort == null || hostAndPort.length != 2) {
                    continue;
                }
                String host = hostAndPort[0];
                String port = hostAndPort[1];
                if (StringUtils.isBlank(host) || StringUtils.isBlank(port)) {
                    continue;
                }
                RemoteInfo remoteInfo = new RemoteInfo();
                remoteInfo.setHost(host);
                remoteInfo.setPort(port);
                remoteInfos.add(remoteInfo);
            }
            return remoteInfos;
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }
}
