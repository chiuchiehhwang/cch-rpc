package hwang.chiuchieh.rpc.util;

import hwang.chiuchieh.rpc.exceptions.CchRpcException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 *
 * IP工具类
 */
public class IPUtils {

    /**
     * 获取本机IP地址
     *
     * @return IP地址
     */
    public static String getLocalIP() {
        try {
            //获取本地IP
            //TODO 获取公网IP
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new CchRpcException("can't get local address", e);
        }
    }

}
