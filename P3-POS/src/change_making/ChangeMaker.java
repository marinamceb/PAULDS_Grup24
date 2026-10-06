package change_making;

import java.util.Map;

public abstract class ChangeMaker {

  // Array de denominaiones según la nota del diagrama
  protected double[] denominations = {0.01, 0.02, 0.05, 0.10, 0.20, 0.50, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0};

  // EL ?? del diagrama es substitueix per un Map per comptar els bitllets/monedes
  public abstract Map<Double, Integer> change(double changeAmount);
}
