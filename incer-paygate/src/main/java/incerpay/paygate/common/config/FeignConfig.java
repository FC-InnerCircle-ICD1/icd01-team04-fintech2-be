package incerpay.paygate.common.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import incerpay.paygate.common.exception.IncerPaymentStoreApiErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new IncerPaymentStoreApiErrorDecoder();
    }

}
