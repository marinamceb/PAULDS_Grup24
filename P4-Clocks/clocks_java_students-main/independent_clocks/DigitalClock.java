package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


// adapted from https://stackoverflow.com/questions/67553152/how-do-i-create-a-clock-using-timer
public class DigitalClock extends Clock implements Runnable {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss S"); // "H:mm:ss a"
  private final int repaintPeriod = 100; // milliseconds
  private int hoursOffsetTimeZone;
  private JLabel clockLabel;

  public DigitalClock(int hoursOffsetTimeZoneOffset, String worldPlace) {
    this.hoursOffsetTimeZone = hoursOffsetTimeZoneOffset;

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

    updateClockLabel();
    run();
  }

  public void show() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void run() {
    Timer timer = new Timer(repaintPeriod, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        updateClockLabel();
      }
    });
    timer.start();
  }

  public void updateClockLabel() {
    LocalDateTime now = LocalDateTime.now().plus(hoursOffsetTimeZone, ChronoUnit.HOURS);
    // see https://www.geeksforgeeks.org/java/localdatetime-plus-method-in-java-with-examples/

    String timeDisplay = now.format(formatter);
    // see https://www.baeldung.com/java-datetimeformatter
    clockLabel.setText(timeDisplay);
    // repaint();
    // it seems there is no need to explicitly call repaint()

  }

}
