package incerpay.paygate.application.service;

import incerpay.paygate.common.lib.clock.ClockManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentGatewayService {

    private final ClockManager clockManager;

}
