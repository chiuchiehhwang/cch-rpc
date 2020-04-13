package hwang.chiuchieh.rpc.config;

import hwang.chiuchieh.rpc.spi.SPI;
import hwang.chiuchieh.rpc.spi.SPIExt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ExtensionLoader<T> {

    //缓存SPI接口与ExtensionLoader的映射
    private static ConcurrentHashMap<Class, ExtensionLoader> loaderCache = new ConcurrentHashMap<>();

    //配置文件的路径
    private static String EXTENSION_FILE_DIRECTORY = "META-INF/cch-rpc/";

    //缓存组件名称与组件Class类型的映射
    private ConcurrentHashMap<String, Class> classCache;

    //SPI接口类型
    private Class type;

    //组件名称与组件实现
    private ConcurrentHashMap<String, T> holder;

    //自适应扩展代理类
    private T adaptiveClass;

    private ExtensionLoader(Class clazz) {
        type = clazz;
        holder = new ConcurrentHashMap<>();
    }

    public static <T> ExtensionLoader<T> getExtesionLoader(Class<T> clazz) {
        if(clazz == null) {
            throw new IllegalArgumentException("class == null");
        }
        ExtensionLoader loader = loaderCache.get(clazz);
        if (loader == null) {
            loaderCache.putIfAbsent(clazz, new ExtensionLoader(clazz));
            loader = loaderCache.get(clazz);
        }
        return loader;
    }

    public T getExtension(String name) {
        if(name == null || name.length() == 0) {
            return getDefaultExtension();
        }
        T instance = holder.get(name);
        if(instance == null) {
            synchronized (this) {
                instance = holder.get(name);
                if(instance == null) {
                    instance = createExtension(name);
                    holder.put(name, instance);
                }
            }
        }
        return instance;
    }

    public T getAdaptiveExtension() {
        if (adaptiveClass == null) {
            synchronized (this) {
                if(adaptiveClass == null) {
                    InvocationHandler handler = new AdaptiveExtensionHandler();
                    adaptiveClass = (T) Proxy.newProxyInstance(
                            handler.getClass().getClassLoader(), new Class[]{type}, handler);
                }
            }
        }
        return adaptiveClass;
    }

    private T getDefaultExtension() {
        SPI spi = (SPI) type.getAnnotation(SPI.class);
        if(spi == null) {
            return null;
        }
        String defaultVal = spi.value();
        if(defaultVal != null && defaultVal.length() != 0) {
            return getExtension(defaultVal);
        }
        return null;
    }

    private T createExtension(String name) {
        if(classCache == null) {
            classCache = loadAllExtensionClass();
        }
        Class clazz = classCache.get(name);
        if(clazz == null) {
            throw new IllegalStateException("no class named " + name);
        }
        T instance = null;
        try {
            instance = (T) clazz.newInstance();
//            instance = (T) Unsafe.getUnsafe().allocateInstance(clazz);
        } catch (Exception e) {
            //TODO
        }
        return instance;
    }

    private ConcurrentHashMap<String, Class> loadAllExtensionClass() {
        ConcurrentHashMap<String, Class> result = new ConcurrentHashMap();
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
                    if (line.length() != 0) {
                        handleLine(line, result);
                    }
                }
            }
        } catch (Exception e) {
            //TODO
        }
        return result;
    }

    private void handleLine(String line, ConcurrentHashMap<String, Class> map) {
        int endIndex = line.indexOf('#');
        if(endIndex == 0) {
            return;
        } else if(endIndex > 0) {
            line = line.substring(0, endIndex).trim();
        } else {
            line = line.trim();
        }
        if (line == null || line.length() == 0) {
            return;
        }
        int splitIndex = line.indexOf('=');
        String name = line.substring(0, splitIndex).trim();
        String value = line.substring(splitIndex + 1).trim();
        if(name.length() == 0 || value.length() == 0) {
            //TODO log or exception
            return;
        }
        try {
            map.put(name, Class.forName(value));
        } catch (Exception e) {
            //TODO
        }
    }

    class AdaptiveExtensionHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            boolean isTarget = false;
            SPIExt spiExt = null;
            for (Object arg : args) {
                if(arg instanceof SPIExt) {
                    isTarget = true;
                    spiExt = (SPIExt) arg;
                    break;
                }
            }
            if(isTarget == false) {
                return null;
            }
            String extensionKey = "extension" + type.getSimpleName();
            String name = spiExt.get(extensionKey);
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtesionLoader(type);
            T subject = extensionLoader.getExtension(name);
            Object result = method.invoke(subject, args);
            return result;
        }
    }
}