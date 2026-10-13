package independent_clocks;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;


public class ClockTimer extends Observable {
  private Timer timer;
  private int period; // milliseconds

  public ClockTimer(int period){
    this.period = period;

    // TimerTask defines the action to perform periodically
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        setChanged(); // Marks this Observable as having been changed
        notifyObservers(LocalDateTime.now()); // Sends the current time to all observers
      }
    };

    timer = new Timer();
    // Schedules the task to run repeatedly at the given period
    timer.scheduleAtFixedRate(timerTask, period, period);
  }

  public int getPeriod(){
    return period;
  }
}
