package incerpay.payment.common.lib.result;

public record Result(
        Object data
) {
    public static Result from(Object data) {
        return new Result(data);
    }
    public static Result ok(){
        return new Result("OK");
    }
}
