package hwang.chiuchieh.rpc.test.module;

public class TestServiceImpl implements TestService {
    @Override
    public String testMethod(String a, String b) {
        return a + " " + b;
    }
}
