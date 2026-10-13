package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Observable;

public class DigitalClock extends Clock {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss S"); // "H:mm:ss a"
  private int hoursOffsetTimeZone;
  private JLabel clockLabel;

  public DigitalClock(int hoursOffsetTimeZoneOffset, String worldPlace, ClockTimer timer) {
    this.hoursOffsetTimeZone = hoursOffsetTimeZoneOffset;
    this.repaintPeriod = 100;

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(5, 45, 5, 45));
    panel.setPreferredSize(new Dimension(500,120));

    clockLabel = new JLabel();
    clockLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 72));
    clockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(clockLabel);

    JLabel placeLabel = new JLabel();
    placeLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
    placeLabel.setText(worldPlace);
    placeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(placeLabel);

    // Register this clock with the single timer
    timer.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg){
    LocalDateTime now = (LocalDateTime) arg;

    // Check rate limiting
    if(isTimeToRepaint(now)){
      this.lastTimeRepaint = now;
      LocalDateTime localNow = now.plus(hoursOffsetTimeZone, ChronoUnit.HOURS);
      clockLabel.setText(localNow.format(formatter));
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
