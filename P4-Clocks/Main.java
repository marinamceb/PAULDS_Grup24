package independent_clocks;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    ClockTimer masterTimer = new ClockTimer(10);

    // this is how to initialize an arrayList
    List<Clock> clocks = new ArrayList<>(List.of(
        new AnalogClock(0, "Cerdanyola, Catalonia", masterTimer),
        new DigitalClock(-9, "Anchorage, Alaska", masterTimer),
        new AnalogClock(3, "Moscow, Russia", masterTimer),
        new DigitalClock(-7, "Sonora, Mexico", masterTimer),
        new AnalogClock(-1, "Berlin, Germany", masterTimer),
        new DigitalClock(-4, "Yerevan, Armenia", masterTimer),

        new CountdownTimer(Duration.ofMinutes(1), masterTimer),

        new Stopwatch(masterTimer)
    ));
    // every clock already works now, we just need to show it
    for (Clock c : clocks) {
      c.show();
    }
  }
}