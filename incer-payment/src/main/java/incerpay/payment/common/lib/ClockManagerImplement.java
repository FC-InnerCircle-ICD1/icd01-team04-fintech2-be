package incerpay.payment.common.lib;

import java.time.Clock;

public class ClockManagerImplement implements ClockManager{
    public static ClockManager of(){
        return new ClockManagerImplement();
    }

    @Override
    public Clock getClock() {
        return Clock.systemDefaultZone();
    }
}
