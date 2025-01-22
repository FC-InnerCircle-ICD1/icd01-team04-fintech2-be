package incerpay.paygate.common.aspect;

import incerpay.paygate.common.auth.AuthorizationPublicKeyVerifier;
import incerpay.paygate.common.auth.AuthorizationSecretKeyVerifier;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class AuthorizationKeyAspect {

    Logger log = LoggerFactory.getLogger(AuthorizationKeyAspect.class);

    private final AuthorizationPublicKeyVerifier authorizationPublicKeyVerifier;
    private final AuthorizationSecretKeyVerifier authorizationSecretKeyVerifier;

    public AuthorizationKeyAspect(AuthorizationPublicKeyVerifier authorizationPublicKeyVerifier,
                                  AuthorizationSecretKeyVerifier authorizationSecretKeyVerifier) {
        this.authorizationPublicKeyVerifier = authorizationPublicKeyVerifier;
        this.authorizationSecretKeyVerifier = authorizationSecretKeyVerifier;
    }


    @Around("@annotation(AuthorizationPublicKeyHeader) || @annotation(AuthorizationSecretKeyHeader)")
    public Object checkAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String apiKey = request.getHeader("Authorization");
        String apiKeyState = request.getHeader("X-Api-Key-State");
        Long sellerId = Long.parseLong(request.getHeader("X-Client-Id"));


        log.info("Authorization apiKey: {}, apiKeyState: {}, sellerId : {}", apiKey, apiKeyState, sellerId);


        if (method.isAnnotationPresent(AuthorizationPublicKeyHeader.class)) {
            authorizationPublicKeyVerifier.verify(sellerId, apiKey, apiKeyState);
        }
        if (method.isAnnotationPresent(AuthorizationSecretKeyHeader.class)) {
            authorizationSecretKeyVerifier.verify(sellerId, apiKey, apiKeyState);
        }

        Object[] args = joinPoint.getArgs();
//        args[0] = sellerId;

        log.info("args: {}, sellerId : {}", Arrays.toString(args), sellerId);

        return joinPoint.proceed(args);
    }
}
