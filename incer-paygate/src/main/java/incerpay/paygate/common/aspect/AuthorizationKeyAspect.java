package incerpay.paygate.common.aspect;

import incerpay.paygate.common.auth.AuthorizationPublicKeyVerifier;
import incerpay.paygate.common.auth.AuthorizationSecretKeyVerifier;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
public class AuthorizationKeyAspect {

    private final AuthorizationPublicKeyVerifier authorizationPublicKeyVerifier;
    private final AuthorizationSecretKeyVerifier authorizationSecretKeyVerifier;

    public AuthorizationKeyAspect(AuthorizationPublicKeyVerifier authorizationPublicKeyVerifier,
                                  AuthorizationSecretKeyVerifier authorizationSecretKeyVerifier) {
        this.authorizationPublicKeyVerifier = authorizationPublicKeyVerifier;
        this.authorizationSecretKeyVerifier = authorizationSecretKeyVerifier;
    }


    @Before("@annotation(AuthorizationPublicKeyHeader) || @annotation(AuthorizationSecretKeyHeader)")
    public void checkHeaders(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String apiKey = request.getHeader("Authorization");

        if (method.isAnnotationPresent(AuthorizationPublicKeyHeader.class)) {
            authorizationPublicKeyVerifier.verify(apiKey);
        }
        if (method.isAnnotationPresent(AuthorizationSecretKeyHeader.class)) {
            authorizationSecretKeyVerifier.verify(apiKey);
        }

    }
}
