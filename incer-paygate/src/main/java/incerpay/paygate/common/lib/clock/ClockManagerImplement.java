package incerpay.paygate.common.lib.clock;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

public class ClockManagerImplement implements ClockManager {
    static ClockManager of(){
        return new ClockManagerImplement();
    }

    @Override
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }

    @Override
    public ChronoLocalDateTime<?> getTodayStart() {
        return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

}
