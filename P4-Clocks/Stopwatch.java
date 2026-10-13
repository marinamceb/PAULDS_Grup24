package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;

public class Stopwatch extends Clock {

  private JLabel timeLabel;
  private JButton startStopButton;
  private boolean isRunning = false;
  private Duration elapsedTime = Duration.ZERO;
  private ClockTimer timerRef;

  public Stopwatch(ClockTimer timer) {
    this.timerRef = timer;
    this.repaintPeriod = 10;

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 45, 20, 45));

    timeLabel = new JLabel("0:00:00:000"); // Starts at 00:00:00:000
    timeLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 48));
    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(timeLabel);

    // Add a button and make it do something when pressed
    startStopButton = new JButton("Start");
    startStopButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    startStopButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        isRunning = !isRunning;
        startStopButton.setText(isRunning ? "Stop" : "Start");
      }
    });
    panel.add(startStopButton);

    timer.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    LocalDateTime now = (LocalDateTime) arg;

    if (isRunning) {
      // Stopwatches prefer the period to add to the elapsed time
      elapsedTime = elapsedTime.plusMillis(timerRef.getPeriod());
    }

    if (isTimeToRepaint(now)) {
      this.lastTimeRepaint = now;

      String formatted = String.format("%d:%02d:%02d:%03d",
          elapsedTime.toHours(),
          elapsedTime.toMinutesPart(),
          elapsedTime.toSecondsPart(),
          elapsedTime.toMillisPart());
      timeLabel.setText(formatted);
    }
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