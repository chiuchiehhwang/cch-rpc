package hwang.chiuchieh.rpc.remoting.cchprotocol.enums;

public enum MsgType {

    RpcRequest(0, "RPC请求, 000"),
    RpcResponse(4, "RPC响应, 100");

    private Integer code;
    private String desc;

    MsgType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MsgType getByCode(Integer code) {
        for (MsgType msgType : values()) {
            if(code == msgType.code) {
                return msgType;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }
}
