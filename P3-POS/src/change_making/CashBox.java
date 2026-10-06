package change_making;

import java.util.Map;
import java.util.TreeMap;

public class CashBox {
  // S'utilitza un TreeMap per mantenir les monedes ordenades
  private Map<Double, Integer> box = new TreeMap<>();

  public CashBox(){
    double[] denominations = {0.01, 0.02, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0};
    for(double d : denominations){
      box.put(d, 0);
    }
  }

  public void addCoins(double denomination, int count){
    box.put(denomination, box.getOrDefault(denomination, 0) + count);
  }

  // Suma a la caixa els bitllets/monedes que entrga el client
  public void addHandedMoney(Map<Double, Integer> handed){
    for(Map.Entry<Double, Integer> entry : handed.entrySet()){
      box.put(entry.getKey(), box.getOrDefault(entry.getKey(), 0) + entry.getValue());
      System.out.println("added payment to cash box\n" + entry.getValue() + " of " + entry.getKey());
    }
  }

  // Retira de la caixa els bitllets/monedes entregats
  public void removeHandedMoney(Map<Double, Integer> handed){
    for (Map.Entry<Double, Integer> entry : handed.entrySet()){
      box.put(entry.getKey(), box.get(entry.getKey()) - entry.getValue());
    }
  }

  // Comprova si la caixa disposa del canvi
  public boolean canGiveChange(Map<Double, Integer> computedChange){
    for (Map.Entry<Double, Integer> entry : computedChange.entrySet()){
      double denom = entry.getKey();
      int requiredCount = entry.getValue();
      // Mira si la quantitat que es disposa és menor que la que es demana
      if (box.getOrDefault(denom, 0) < requiredCount){
        return false;
      }
    }
    return true;
  }

  // Resta de la caixa el canvi
  public void giveChange(Map<Double, Integer> computedChange){
    for (Map.Entry<Double, Integer> entry : computedChange.entrySet()){
      box.put(entry.getKey(), box.get(entry.getKey()) - entry.getValue());
    }
  }

  // Imprimeix l'estat de la caixa
  public void printStatus(){
    for (Map.Entry<Double, Integer> entry : box.entrySet()){
      if (entry.getValue() > 0){
        System.out.println(entry.getValue() + " of " + entry.getKey());
      }
    }
  }
}
