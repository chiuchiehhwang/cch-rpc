package hwang.chiuchieh.rpc.test;

import hwang.chiuchieh.rpc.config.*;
import hwang.chiuchieh.rpc.test.api.TestService;

public class ReferTest {

    public void refer() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-app");

        ProtocolConfig protocolConfig = new ProtocolConfig();

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setHost("127.0.0.1");
        registryConfig.setPort("2334");

        ReferenceBean<TestService> referenceConfig = new ReferenceBean<>();
        referenceConfig.setClazz(TestService.class);
        referenceConfig.setApplicationConfig(applicationConfig);
        referenceConfig.setProtocolConfig(protocolConfig);
        referenceConfig.setRegistryConfig(registryConfig);

        TestService testService = referenceConfig.refer();

        String result = testService.testMethod("aaa", "bbb");
        System.out.println(result);
    }

    public static void main(String[] args) {
        ReferTest test = new ReferTest();
        test.refer();
    }
}