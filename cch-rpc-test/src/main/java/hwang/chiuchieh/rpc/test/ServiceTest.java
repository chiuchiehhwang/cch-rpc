package hwang.chiuchieh.rpc.test;

import hwang.chiuchieh.rpc.config.*;
import org.junit.Test;

public class ServiceTest {

    @Test
    public void export() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-app");

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("cch");
        protocolConfig.setPort("2333");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setName("zookeeper");
        registryConfig.setHost("127.0.0.1");
        registryConfig.setPort("2334");

        ServiceBean<TestService> serviceConfig = new ServiceBean<>();
        serviceConfig.setInterfaceName("hwang.chiuchieh.rpc.test.TestService");
        serviceConfig.setRef(new TestServiceImpl());
        serviceConfig.setApplicationConfig(applicationConfig);
        serviceConfig.setProtocolConfig(protocolConfig);
        serviceConfig.setRegistryConfig(registryConfig);

        serviceConfig.export();
    }

    @Test
    public void refer() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-app");

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("cch");
        protocolConfig.setPort("2333");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setName("zookeeper");
        registryConfig.setHost("127.0.0.1");
        registryConfig.setPort("2334");

        ReferenceBean<TestService> referenceConfig = new ReferenceBean<>();
        referenceConfig.setInterfaceName("hwang.chiuchieh.rpc.test.TestService");
        referenceConfig.setClazz(TestService.class);
        referenceConfig.setApplicationConfig(applicationConfig);
        referenceConfig.setProtocolConfig(protocolConfig);
        referenceConfig.setRegistryConfig(registryConfig);

        TestService testService = referenceConfig.refer();

        testService.testMethod("aaa", "bbb");
    }
}