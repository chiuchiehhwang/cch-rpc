package hwang.chiuchieh.rpc.util;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 *
 * String工具类
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
