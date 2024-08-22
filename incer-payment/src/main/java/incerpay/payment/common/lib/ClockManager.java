package incerpay.payment.common.lib;

import java.time.Clock;

public interface ClockManager {
    static ClockManager of(){
        return ClockManagerImplement.of();
    }

    Clock getClock();
}
