package hwang.chiuchieh.rpc.config.spring;

import hwang.chiuchieh.rpc.config.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class CchrpcBeanDefinitionParser implements BeanDefinitionParser {

    Class<?> beanClass;

    public CchrpcBeanDefinitionParser(Class beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        if(ApplicationConfig.class.equals(beanClass)) {
            beanDefinition.getPropertyValues().add("name", element.getAttribute("name"));
        }
        if(RegistryConfig.class.equals(beanClass)) {
            beanDefinition.getPropertyValues().add("name", element.getAttribute("name"));
            beanDefinition.getPropertyValues().add("host", element.getAttribute("host"));
            beanDefinition.getPropertyValues().add("port", element.getAttribute("port"));
        }
        if(ProtocolConfig.class.equals(beanClass)) {
            beanDefinition.getPropertyValues().add("name", element.getAttribute("name"));
        }
        if(ServiceBean.class.equals(beanClass)) {
            beanDefinition.getPropertyValues().add("interfaceName", element.getAttribute("interface"));
            beanDefinition.getPropertyValues().add("ref", element.getAttribute("ref"));
        }
        if(ReferenceBean.class.equals(beanClass)) {
            beanDefinition.getPropertyValues().add("id", element.getAttribute("id"));
            beanDefinition.getPropertyValues().add("interfaceName", element.getAttribute("interface"));
        }

        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        beanDefinitionRegistry.registerBeanDefinition(beanClass.getName(),beanDefinition);

        return beanDefinition;
    }
}
