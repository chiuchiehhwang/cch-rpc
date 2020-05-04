package hwang.chiuchieh.rpc.remoting.cchprotocol.enums;

public enum FailType {

    //业务异常
    BizException(0, "业务异常"),
    //框架异常
    NoProviderFind(100, "没有找到Provider");


    private Integer code;
    private String desc;

    FailType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FailType getByCode(Integer code) {
        for (FailType failType : values()) {
            if(code == failType.code) {
                return failType;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
