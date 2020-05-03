package hwang.chiuchieh.rpc.test;

import hwang.chiuchieh.rpc.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigTest {

    public void testXmlConfig() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:XmlConfig.xml");
        ApplicationConfig applicationConfig =
                (ApplicationConfig) applicationContext.getBean(ApplicationConfig.class.getName());
        System.out.println(applicationConfig.getName());
    }

    public static void main(String[] args) {
        XmlConfigTest xmlConfigTest = new XmlConfigTest();
        xmlConfigTest.testXmlConfig();
    }

}