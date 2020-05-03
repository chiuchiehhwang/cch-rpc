package hwang.chiuchieh.rpc.util;

import hwang.chiuchieh.rpc.exceptions.CchRpcException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {

    public static String getLocalIP() {
        try {
            //获取本地IP
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new CchRpcException("can't get local address", e);
        }
    }

}
