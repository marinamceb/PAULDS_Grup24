package change_making;

import java.util.Map;
import java.util.TreeMap;

public class CashBox {
  // S'utilitza un TreeMap per mantenir les monedes ordenades
  private Map<Double, Integer> box = new TreeMap<>();

  public void addCoins(double denomination, int count) {
    box.put(denomination, box.getOrDefault(denomination, 0) + count);
  }

  // Suma a la caixa els bitllets/monedes que entrega el client
  public void addHandedMoney(Map<Double, Integer> handed) {
    System.out.println("added payment to cash box");
    for (Map.Entry<Double, Integer> entry : handed.entrySet()) {
      addCoins(entry.getKey(), entry.getValue());
      System.out.println(entry.getValue() + " of " + entry.getKey());
    }
  }

  // Retira de la caixa els bitllets/monedes entregats pel client
  public void removeHandedMoney(Map<Double, Integer> handed) {
    subtract(handed);
  }

  // Comprova si la caixa disposa del canvi
  public boolean canGiveChange(Map<Double, Integer> computedChange) {
    for (Map.Entry<Double, Integer> entry : computedChange.entrySet()) {
      if (box.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
        return false;
      }
    }
    return true;
  }

  // Resta de la caixa el canvi
  public void giveChange(Map<Double, Integer> computedChange) {
    subtract(computedChange);
  }

  private void subtract(Map<Double, Integer> money) {
    for (Map.Entry<Double, Integer> entry : money.entrySet()) {
      box.put(entry.getKey(), box.getOrDefault(entry.getKey(), 0) - entry.getValue());
    }
  }

  // Imprimeix l'estat de la caixa (només les denominacions amb existències)
  public void printStatus() {
    for (Map.Entry<Double, Integer> entry : box.entrySet()) {
      if (entry.getValue() > 0) {
        System.out.println(entry.getValue() + " of " + entry.getKey());
      }
    }
  }
}