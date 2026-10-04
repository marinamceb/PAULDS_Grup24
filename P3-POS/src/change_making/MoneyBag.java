package change_making;

import java.util.Map;
import java.util.TreeMap;

public class MoneyBag {

  // ATRIBUTS
  private Map<Double, Integer> money;

  // CONSTRUCTOR
  public MoneyBag() {
    this.money = new TreeMap<>(); // TreeMap ordena les claus del diccionari de petit a gran (en el nostre programa, quedarà {0.01, 0.02, ..., 20.0, 50.0})
  }

  // MÈTODES
  public Map<Double, Integer> getMoney() {
    return money;
  }
  public void add(double value, int count) { // Afegeix diners a la bossa
    int currentCount = money.getOrDefault(value, 0); // Retorna la quantitat de diners que hi ha a la bossa amb el valor "value"
    money.put(value, currentCount + count); // Suma la quantitat de diners amb valor "value" a la bossa
  }
  public void add(MoneyBag moneyBag) { // Afegeix diners a la bossa
    for (Map.Entry<Double, Integer> entry : moneyBag.getMoney().entrySet()) { // Recorre tots els elements de moneyBag
      this.add(entry.getKey(), entry.getValue()); // Afegeix a la bossa els diners de moneyBag
    }
  }
  public void subtract(MoneyBag moneyBag) { // Extreu diners a la bossa
    for (Map.Entry<Double, Integer> entry : moneyBag.getMoney().entrySet()) { // Recorre tots els elements de moneyBag
      double entryValue = entry.getKey(); // Valor del bitllet o moneda iterat
      int countToSubtract = entry.getValue(); // Quantitat d'aquell valor a restar
      int currentCount = money.getOrDefault(entryValue, 0); // Quantitat del valor amb el que comptem

      int countDifference = currentCount - countToSubtract;
      if(countDifference <= 0) { // Si no hi ha diners suficients o ens quedem a 0
        money.remove(entryValue); // Elimina la clau del mapa
      }
      else {
        money.put(entryValue, countDifference); // Actualitza la quantitat de diners amb valor "value"
      }
    }
  }
  public double total() { // Retorna el total de diners de la bossa
    double sum = 0.0;
    for(Map.Entry<Double,Integer> entry : money.entrySet()) {
      sum += entry.getKey() * entry.getValue();
    }
    return Math.round(sum * 100.0) / 100.0; //Arrodonim a 2 valors per evitar errors de precisió del double
  }
  public boolean contains(MoneyBag required) { // Retorna si la bossa conté els diners d'una altra bossa
    for(Map.Entry<Double,Integer> entry : required.getMoney().entrySet()) {
      double requiredValue = entry.getKey();
      int requiredCount = entry.getValue();
      int currentCount = money.getOrDefault(requiredValue, 0);
      if(currentCount < requiredCount) {
        return false; // No hi ha diners suficients del valor "requiredValue"
      }
    }
    return true; // Hi ha diners suficients de tot el que necessita la bossa de diners "required"
  }

  @Override
  public String toString() { // Passa els diners de la bossa a text
    if(money.isEmpty()) {
      return "empty";
    }
    StringBuilder sb = new StringBuilder();
    for(Map.Entry<Double, Integer> entry : money.entrySet()) { // Per cada value
      sb.append(entry.getValue()).append(" of ").append(entry.getKey()).append(" "); // "<count> of <value>" (p.e.: 2 of 5.0)
    }
    return sb.toString().trim(); // Retorna la cadena de text amb tots els valors del Map
  }
  public void print() {
    System.out.println(this.toString()); // Imprimeix l'string de la bossa de diners
  }
}
