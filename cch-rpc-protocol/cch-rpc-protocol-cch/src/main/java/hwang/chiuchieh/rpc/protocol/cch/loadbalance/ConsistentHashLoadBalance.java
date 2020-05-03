package hwang.chiuchieh.rpc.protocol.cch.loadbalance;

import com.google.gson.Gson;
import hwang.chiuchieh.rpc.Invocation;
import hwang.chiuchieh.rpc.Invoker;
import hwang.chiuchieh.rpc.RemoteInfo;
import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.protocol.api.LoadBalance;
import hwang.chiuchieh.rpc.spi.SPIExt;
import hwang.chiuchieh.rpc.util.IPUtils;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.List;
import java.util.TreeMap;

@Slf4j
public class ConsistentHashLoadBalance implements LoadBalance {

    private static final int MD5_OFFSET = 4;

    @Override
    public RemoteInfo load(Invocation invocation, List<RemoteInfo> remoteInfos, SPIExt spiExt) {
        String callKey = getInvokerHashKey(invocation);
        byte[] callMd5 = getMD5Bytes(callKey);
        long callIndex = getLongValue(0, callMd5);

        TreeMap<Long, RemoteInfo> hashContainer = new TreeMap<>();
        for (RemoteInfo remoteInfo : remoteInfos) {
            byte[] addrMd5 = getMD5Bytes(remoteInfo.toString());
            for (int i = 0; i + 4 < addrMd5.length; i += MD5_OFFSET) {
                long hashIndex = getLongValue(i, addrMd5);
                hashContainer.put(hashIndex, remoteInfo);
            }
        }

        return hashContainer.get(hashContainer.ceilingKey(callIndex));
    }

    private String getInvokerHashKey(Invocation invocation) {
        String key;
        Invoker invoker = invocation.getInvoker();
        String argsKey = new Gson().toJson(invocation.getArgsValues());
        String localIP = IPUtils.getLocalIP();
        key = localIP + invoker.getInterfaceName() + invocation.getMethodName() + argsKey;
        return key;
    }

    private byte[] getMD5Bytes(String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception e) {
            log.error("generate md5 bytes error", e);
            throw new CchRpcException(e);
        }

    }

    private long getLongValue(int index, byte[] md5) {
        long index1 = (md5[index] & 0xFF) << 24;
        long index2 = (md5[index] & 0xFF) << 16;
        long index3 = (md5[index] & 0xFF) << 8;
        long index4 = (md5[index] & 0xFF);
        return index1 | index2 | index3 | index4;
    }
}