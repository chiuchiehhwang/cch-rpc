package hwang.chiuchieh.rpc.remoting.cchprotocol.enums;

public enum SerializationType {

    gson(0, "gson, 00000");

    private Integer code;
    private String desc;

    SerializationType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SerializationType getByCode(Integer code) {
        for (SerializationType msgType : values()) {
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
