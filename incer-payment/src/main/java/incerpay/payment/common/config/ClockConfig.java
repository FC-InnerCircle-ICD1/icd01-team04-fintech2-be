package incerpay.payment.common.config;

import incerpay.payment.common.lib.clock.ClockManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfig {
    @Bean
    public ClockManager clockManager(){
        return ClockManager.of();
    }
}
