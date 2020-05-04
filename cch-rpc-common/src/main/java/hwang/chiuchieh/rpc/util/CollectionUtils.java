package hwang.chiuchieh.rpc.util;

import java.util.Collection;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 *
 * Collection工具类
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }
}
