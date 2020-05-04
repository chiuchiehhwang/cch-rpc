package hwang.chiuchieh.rpc.test;

import hwang.chiuchieh.rpc.config.*;
import hwang.chiuchieh.rpc.test.api.TestService;

public class ExportTest {

    public void export() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-app");

        ProtocolConfig protocolConfig = new ProtocolConfig();

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setHost("127.0.0.1");
        registryConfig.setPort("2334");

        ServiceBean<TestService> serviceConfig = new ServiceBean<>();
        serviceConfig.setClazz(TestService.class);
        serviceConfig.setRef(new TestServiceImpl());
        serviceConfig.setApplicationConfig(applicationConfig);
        serviceConfig.setProtocolConfig(protocolConfig);
        serviceConfig.setRegistryConfig(registryConfig);

        serviceConfig.export();

        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("System.in.read()发生异常");
        }
    }

    public static void main(String[] args) {
        ExportTest test = new ExportTest();
        test.export();
    }
}