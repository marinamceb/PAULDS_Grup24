package independent_clocks;

import javax.swing.*;

public abstract class Clock {
  // this is just to have in Main.java a list with digital and analog clocks

  protected JPanel panel;
  // this is a container class, contains all the elements of the UI

  public abstract void show();
  // every Clock subclass must implement a show()
}
