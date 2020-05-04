package hwang.chiuchieh.rpc.spi;

import hwang.chiuchieh.rpc.exceptions.CchRpcException;
import hwang.chiuchieh.rpc.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @param <T> SPI接口类型
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * SPI实例加载器
 */
public class ExtensionLoader<T> {

    //缓存 SPI接口与ExtensionLoader的映射
    private static ConcurrentHashMap<Class, ExtensionLoader> loaderCache = new ConcurrentHashMap<>();

    //配置文件的路径
    private static final String EXTENSION_FILE_DIRECTORY = "META-INF/cch-rpc/";

    //缓存 实现类名称与实现类Class类型的映射
    private ConcurrentHashMap<String, Class> classCache;

    //SPI接口类型
    private Class<T> type;

    //组件名称与组件实现
    private ConcurrentHashMap<String, T> instanceHolder;

    //自适应扩展代理类
    private T adaptiveClass;

    private ExtensionLoader(Class<T> clazz) {
        type = clazz;
        instanceHolder = new ConcurrentHashMap<>();
    }

    /**
     * 获取某个SPI接口的实例加载器
     *
     * @param clazz SPI接口
     * @param <T>   SPI接口类型
     * @return 加载器
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> clazz) {
        if (clazz == null) {
            throw new CchRpcException("class cannot be empty");
        }
        ExtensionLoader<T> loader = loaderCache.get(clazz);
        if (loader == null) {
            loaderCache.putIfAbsent(clazz, new ExtensionLoader(clazz));
            loader = loaderCache.get(clazz);
        }
        return loader;
    }

    /**
     * 根据实现类名称获取实例
     *
     * @param name 实现类名称
     * @return 实例
     */
    public T getExtension(String name) {
        if (StringUtils.isEmpty(name)) {
            return getDefaultExtension();
        }
        T instance = instanceHolder.get(name);
        if (instance == null) {
            synchronized (this) {
                instance = instanceHolder.get(name);
                if (instance == null) {
                    instance = createExtension(name);
                    instanceHolder.put(name, instance);
                }
            }
        }
        return instance;
    }

    /**
     * 获取自适应扩展类
     *
     * @return 自适应扩展类实例
     */
    public T getAdaptiveExtension() {
        if (adaptiveClass == null) {
            synchronized (this) {
                if (adaptiveClass == null) {
                    InvocationHandler handler = new AdaptiveExtensionHandler();
                    adaptiveClass = (T) Proxy.newProxyInstance(
                            handler.getClass().getClassLoader(), new Class[]{type}, handler);
                }
            }
        }
        return adaptiveClass;
    }

    private T getDefaultExtension() {
        SPI spi = type.getAnnotation(SPI.class);
        if (spi == null) {
            return null;
        }
        String defaultVal = spi.value();
        if (StringUtils.isNotEmpty(defaultVal)) {
            return getExtension(defaultVal);
        }
        return null;
    }

    private T createExtension(String name) {
        if (classCache == null) {
            classCache = loadAllExtensionClass();
        }
        Class clazz = classCache.get(name);
        if (clazz == null) {
            throw new CchRpcException("no class named " + name);
        }
        T instance = null;
        try {
            instance = (T) clazz.newInstance();
//            instance = (T) Unsafe.getUnsafe().allocateInstance(clazz);
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
        return instance;
    }

    private ConcurrentHashMap<String, Class> loadAllExtensionClass() {
        ConcurrentHashMap<String, Class> result = new ConcurrentHashMap<>();
        String fileName = EXTENSION_FILE_DIRECTORY + type.getName();
        try {
            Enumeration<java.net.URL> URLS = ClassLoader.getSystemClassLoader().getResources(fileName);
            while (URLS.hasMoreElements()) {
                java.net.URL url = URLS.nextElement();
                BufferedReader bf =
                        new BufferedReader(
                                new InputStreamReader(url.openStream(), "utf-8"));
                String line;
                while ((line = bf.readLine()) != null) {
                    if (StringUtils.isNotEmpty(line)) {
                        handleLine(line, result);
                    }
                }
            }
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
        return result;
    }

    private void handleLine(String line, ConcurrentHashMap<String, Class> map) {
        int endIndex = line.indexOf('#');
        if (endIndex == 0) {
            return;
        } else if (endIndex > 0) {
            line = line.substring(0, endIndex).trim();
        } else {
            line = line.trim();
        }
        if (StringUtils.isEmpty(line)) {
            return;
        }
        int splitIndex = line.indexOf('=');
        String name = line.substring(0, splitIndex).trim();
        String value = line.substring(splitIndex + 1).trim();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            throw new CchRpcException("incorrect SPI profile");
        }
        try {
            map.put(name, Class.forName(value));
        } catch (Exception e) {
            throw new CchRpcException(e);
        }
    }

    /**
     * 自适应扩展类代理逻辑
     */
    class AdaptiveExtensionHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            boolean isTarget = false;
            SPIExt spiExt = null;
            for (Object arg : args) {
                if (arg instanceof SPIExt) {
                    isTarget = true;
                    spiExt = (SPIExt) arg;
                    break;
                }
            }
            String name = null;
            if (isTarget == true) {
                String extensionKey = "extension" + type.getSimpleName();
                name = spiExt.get(extensionKey);
            }
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(type);
            T subject = extensionLoader.getExtension(name);
            Object result = method.invoke(subject, args);
            return result;
        }
    }
}