package change_making;

public class GreedyChangeMaker extends ChangeMaker {

  // MÈTODES
  @Override
  public MoneyBag change(double change) {
    MoneyBag result = new MoneyBag();
    if(change <= 0.0) {
      return result;
    }

    int remainingChange = (int) Math.round(change * 100.0); // Arrodonim a cèntims per evitar errors de precisió pel double

    for(int i=DENOMINATIONS.length-1; i>=0; i--) { // Recorre els valors de major a menor

      double value = DENOMINATIONS[i]; // Valor dels diners en euros
      int valueCents = (int) Math.round(value * 100.0); // Valor dels diners en cèntims

      if(remainingChange >= valueCents) { // Si els diners amb valor "value" hi quep en el canvi a donar
        int count = remainingChange / valueCents; // Nombre de diners de valor "value" que hi quep al canvi a donar
        result.add(value, count); // Afegeix el "count" de diners de valor "value" al canvi
        remainingChange -= count * valueCents; // Resta el canvi que falta per donar
      }
    }

    return result;
  }
}
