package hwang.chiuchieh.rpc.protocol.cch.cluster;

import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.protocol.api.Cluster;
import hwang.chiuchieh.rpc.protocol.api.LoadBalance;
import hwang.chiuchieh.rpc.registry.api.Registry;
import hwang.chiuchieh.rpc.spi.ExtensionLoader;
import hwang.chiuchieh.rpc.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractCluster implements Cluster {
    /**
     * 注册中心实现，通过自适应扩展机制获取
     */
    protected static Registry REGISTRY =
            ExtensionLoader.getExtensionLoader(Registry.class).getAdaptiveExtension();

    /**
     * 路由策略，通过自适应扩展机制获取
     */
    protected static LoadBalance LOAD_BALANCE =
            ExtensionLoader.getExtensionLoader(LoadBalance.class).getAdaptiveExtension();

    //TODO 可以考虑更复杂、更精确的规则
    protected List<RemoteInfo> route(Invocation invocation, List<RemoteInfo> remoteInfos) {
        Invoker invoker = invocation.getInvoker();
        String routeRule = invoker.getRouteRule();
        //如果routeRule字段为空，则不进行路由过滤
        if (StringUtils.isBlank(routeRule)) {
            return remoteInfos;
        }
        Set<RemoteInfo> newRemoteInfos = new HashSet<>();
        String[] rules = routeRule.split(",");
        for (String rule : rules) {
            newRemoteInfos.addAll(route(rule, remoteInfos));
        }

        List<RemoteInfo> result = new ArrayList<>();
        result.addAll(newRemoteInfos);
        return result;
    }

    private Set<RemoteInfo> route(String rule, List<RemoteInfo> remoteInfos) {
        //如果某条规则为空，说明为非法规则，返回空
        if (StringUtils.isBlank(rule)) {
            return new HashSet<>();
        }
        if (rule.startsWith("*")) {
            return getSuffix(rule.substring(1), remoteInfos);
        } else if (rule.endsWith("*")) {
            return getPrefix(rule.substring(0, rule.length() - 1), remoteInfos);
        } else if (!rule.contains("*")) {
            return getPrecise(rule, remoteInfos);
        }
        return new HashSet<>();
    }

    private Set<RemoteInfo> getPrefix(String prefix, List<RemoteInfo> remoteInfos) {
        Set<RemoteInfo> newRemoteInfos = new HashSet<>();
        for (RemoteInfo remoteInfo : remoteInfos) {
            if (remoteInfo.getHost().startsWith(prefix)) {
                newRemoteInfos.add(remoteInfo);
            }
        }
        return newRemoteInfos;
    }

    private Set<RemoteInfo> getSuffix(String suffix, List<RemoteInfo> remoteInfos) {
        Set<RemoteInfo> newRemoteInfos = new HashSet<>();
        for (RemoteInfo remoteInfo : remoteInfos) {
            if (remoteInfo.getHost().endsWith(suffix)) {
                newRemoteInfos.add(remoteInfo);
            }
        }
        return newRemoteInfos;
    }

    private Set<RemoteInfo> getPrecise(String addr, List<RemoteInfo> remoteInfos) {
        Set<RemoteInfo> newRemoteInfos = new HashSet<>();
        for (RemoteInfo remoteInfo : remoteInfos) {
            if (remoteInfo.getHost().endsWith(addr)) {
                newRemoteInfos.add(remoteInfo);
            }
        }
        return newRemoteInfos;
    }
}
