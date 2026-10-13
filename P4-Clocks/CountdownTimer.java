package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Observable;

public class CountdownTimer extends Clock{
  private JLabel timeLabel;
  private LocalDateTime endTime;
  private Duration initialDuration;

  public CountdownTimer(Duration duration, ClockTimer timer) {
    this.initialDuration = duration;
    this.repaintPeriod = 1000;

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 45, 20, 45));

    timeLabel = new JLabel();
    timeLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 48));
    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(timeLabel);

    updateDisplay(duration);

    timer.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg){
    LocalDateTime now = (LocalDateTime) arg;

    if(endTime == null){
      // Set the target end time when the timer begins ticking
      endTime = now.plus(initialDuration);
    }

    if (isTimeToRepaint(now)){
      this.lastTimeRepaint = now;
      Duration remaining = Duration.between(now, endTime);

      if(remaining.isNegative()){
        remaining = Duration.ZERO;
      }

      updateDisplay(remaining);
    }
  }

  private void updateDisplay(Duration remaining) {
    // Convert a Duration to string[cite: 18]
    String formattedCountdown = String.format("%d:%02d:%02d",
        remaining.toHours(),
        remaining.toMinutesPart(),
        remaining.toSecondsPart());
    timeLabel.setText(formattedCountdown);
  }

  @Override
  public void show() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }
}
