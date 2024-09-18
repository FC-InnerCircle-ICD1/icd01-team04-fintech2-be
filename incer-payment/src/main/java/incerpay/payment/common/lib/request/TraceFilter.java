package incerpay.payment.common.lib.request;

import incerpay.payment.common.lib.clock.ClockManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.Clock;

public class TraceFilter implements Filter {
    private final ClockManager clockManager;

    public TraceFilter(ClockManager clockManager) {
        this.clockManager = clockManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Clock clock = clockManager.getClock();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getRequestURI().contains("/swagger-ui/")
                || request.getRequestURI().contains("/v3/api-docs")
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String traceId = request.getHeader("traceId");
        TraceThreadHelper.setTraceId(traceId);
        String spanId = "" + clock.instant().toEpochMilli();
        TraceThreadHelper.setSpanId(spanId);
        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            TraceThreadHelper.clear();
        }
    }
}
