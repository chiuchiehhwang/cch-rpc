package hwang.chiuchieh.rpc.spi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Chiuchieh Hwang
 * @date 2020/05/04
 * <p>
 * SPI接口注解
 * <p>
 * 声明一个SPI接口时，需在接口上使用@SPI注解；
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SPI {

    /**
     * @return 默认加载类的名称
     */
    String value() default "";

}
