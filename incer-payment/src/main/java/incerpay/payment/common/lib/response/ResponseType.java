package incerpay.payment.common.lib.response;

public enum ResponseType {
    OK(200, "OK"),
    FAIL(400, "FAIL"),
    ERROR(500, "ERROR")
    ;

    private final int resultCode;
    private final String resultMsg;

    ResponseType(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
    public int resultCode() {
        return resultCode;
    }
    public String resultMsg() {
        return resultMsg;
    }
}
