package incerpay.payment.common.lib.request;

public class TraceStateHelper {
    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> SPAN_ID = new ThreadLocal<>();

    public static void setUserId(String userId) {
        USER_ID.set(userId);
    }
    public static String getUserId() {
        return USER_ID.get();
    }
    public static void setSpanId(String spanId) {
        SPAN_ID.set(spanId);
    }
    public static String getSpanId() {
        return SPAN_ID.get();
    }
    public static void clear() {
        USER_ID.remove();
        SPAN_ID.remove();
    }
}
