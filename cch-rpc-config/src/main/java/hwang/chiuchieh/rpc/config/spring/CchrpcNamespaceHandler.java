package hwang.chiuchieh.rpc.config.spring;

import hwang.chiuchieh.rpc.config.*;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CchrpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("application", new CchrpcBeanDefinitionParser(ApplicationConfig.class));
        registerBeanDefinitionParser("registry", new CchrpcBeanDefinitionParser(RegistryConfig.class));
        registerBeanDefinitionParser("protocol", new CchrpcBeanDefinitionParser(ProtocolConfig.class));
        registerBeanDefinitionParser("service", new CchrpcBeanDefinitionParser(ServiceConfig.class));
        registerBeanDefinitionParser("reference", new CchrpcBeanDefinitionParser(ReferenceConfig.class));
    }
}
