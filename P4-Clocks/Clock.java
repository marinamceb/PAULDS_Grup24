package independent_clocks;

import javax.swing.*;
import java.util.Observer;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class Clock implements Observer{ // Implement Observer interface

  protected JPanel panel;
  protected LocalDateTime lastTimeRepaint; // Track last update
  protected int repaintPeriod; // How often this specific clock should update

  public abstract void show();

  // To check if enough time has passed to warrant a repaint
  protected boolean isTimeToRepaint(LocalDateTime now){
    // Repaint if it's the very first time or if the required period has elapsed
    return (this.lastTimeRepaint == null) || (now.minus(this.repaintPeriod, ChronoUnit.MILLIS).isAfter(this.lastTimeRepaint));
  }
}