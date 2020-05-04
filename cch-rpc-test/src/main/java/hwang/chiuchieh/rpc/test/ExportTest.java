package hwang.chiuchieh.rpc.test;

import hwang.chiuchieh.rpc.config.*;

public class ExportTest {

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