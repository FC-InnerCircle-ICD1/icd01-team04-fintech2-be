package incerpay.payment.common.lib.clock;

import java.time.Clock;
import java.time.chrono.ChronoLocalDateTime;

public interface ClockManager {
    static ClockManager of(){
        return ClockManagerImplement.of();
    }

    Clock getClock();

    ChronoLocalDateTime<?> getTodayStart();
}
