package independent_clocks;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// adapted from https://stackoverflow.com/questions/67553152/how-do-i-create-a-clock-using-timer
public class AnalogClock extends Clock {
  private final int repaintPeriod = 1000; // milliseconds
  private int hoursOffsetTimeZone;
  private String worldPlace;


  public AnalogClock(int hoursOffsetTimeZone, String worldPlace) {
    this.worldPlace = worldPlace;
    this.hoursOffsetTimeZone = hoursOffsetTimeZone;
    panel = new MyJPanel();
    run();
  }

  public void show() {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
  }

  private void run() {
    new Timer(repaintPeriod, e -> panel.repaint()).start();
    // this is a java.swing.Timer object, not a java.util.Timer !
    // repaint() calls paintComponent()
  }

  // we need to override paintComponent() of JPanel so we make a private class MyJPanel
  private class MyJPanel extends JPanel {
    public MyJPanel() {
      super();
      setPreferredSize(new Dimension(400, 300));
      setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      int side = Math.min(getWidth(), getHeight());
      int centerX = getWidth() / 2;
      int centerY = getHeight() / 2;

      LocalDateTime now = LocalDateTime.now().plus(hoursOffsetTimeZone, ChronoUnit.HOURS);
      // see https://www.geeksforgeeks.org/java/localdatetime-plus-method-in-java-with-examples/

      int second = now.getSecond();
      int minute = now.getMinute();
      int hour = now.getHour();

      drawHand(g2d, side / 2 - 10, second / 60.0, 0.5f, Color.RED);
      drawHand(g2d, side / 2 - 20, (minute + second/60.) / 60.0, 2.0f, Color.BLUE);
      drawHand(g2d, side / 2 - 40, (hour + minute/60.) / 12.0, 4.0f, Color.BLACK);

      // Draw clock numbers and circle
      drawClockFace(g2d, centerX, centerY, side / 2 - 40);
    }

    private void drawHand(Graphics2D g2d, int length, double value, float stroke, Color color) {
      double angle = Math.PI * 2 * (value - 0.25);
      int endX = (int) (getWidth() / 2 + length * Math.cos(angle));
      int endY = (int) (getHeight() / 2 + length * Math.sin(angle));

      g2d.setColor(color);
      g2d.setStroke(new BasicStroke(stroke));
      g2d.drawLine(getWidth() / 2, getHeight() / 2, endX, endY);
    }

    // Added method to draw the clock face and numbers
    private void drawClockFace(Graphics2D g2d, int centerX, int centerY, int radius) {
      g2d.setStroke(new BasicStroke(2.0f));
      g2d.setColor(Color.BLACK);
      g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

      for (int i = 1; i <= 12; i++) {
        double angle = Math.PI * 2 * (i / 12.0 - 0.25);
        int dx = centerX + (int) ((radius + 20) * Math.cos(angle));
        int dy = centerY + (int) ((radius + 20) * Math.sin(angle));

        g2d.drawString(Integer.toString(i), dx, dy); // draws the hour numbers 1...12
      }

      g2d.drawString(worldPlace, (int) (radius * 1.2), (int) (radius * 2.0));
    }
  }

}
