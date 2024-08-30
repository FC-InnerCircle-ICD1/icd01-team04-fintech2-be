package incerpay.payment.common.lib.response;

public record Response(
        Integer resultCode,
        String resultMsg,
        Object data
) {
    public static Response ok(Object data) {
        ResponseType responseType = ResponseType.OK;
        return new Response(responseType.resultCode(), responseType.resultMsg(), data);
    }
    public static Response fail(Object data) {
        ResponseType responseType = ResponseType.FAIL;
        return new Response(responseType.resultCode(), responseType.resultMsg(), data);
    }
    public static Response error(Object data) {
        ResponseType responseType = ResponseType.ERROR;
        return new Response(responseType.resultCode(), responseType.resultMsg(), data);
    }
}
