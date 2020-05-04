package hwang.chiuchieh.rpc.test;

import hwang.chiuchieh.rpc.test.api.TestService;

public class TestServiceImpl implements TestService {
    @Override
    public String testMethod(String a, String b) {
        return "Hello, this is CCH-RPC's testMethod, your string is \"" + a + "\" and \"" + b +"\".";
    }
}
