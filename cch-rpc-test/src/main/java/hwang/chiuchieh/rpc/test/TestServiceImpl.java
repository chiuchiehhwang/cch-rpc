package hwang.chiuchieh.rpc.test;

public class TestServiceImpl implements TestService {
    @Override
    public String testMethod(String a, String b) {
        return a + " " + b;
    }
}
