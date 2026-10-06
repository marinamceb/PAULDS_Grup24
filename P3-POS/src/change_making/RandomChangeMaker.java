package change_making;

import java.util.*;

public class RandomChangeMaker extends ChangeMaker{
  @Override
  public Map<Double, Integer> change(double changeAmount){
    Map<Double, Integer> result = new LinkedHashMap<>();

    //Multipliquem x100 per treballar en centims i evitar possibles errors
    long remaining = Math.round(changeAmount * 100);
    Random random = new Random();

    while(remaining > 0){
      // Es busca quines denominacions caben en el canvi restant
      List<Double> validDenom = new ArrayList<>();
      for (double denom : denominations) {
        if (Math.round(denom * 100) <= remaining){
          validDenom.add(denom);
        }
      }

      // No hi ha cap vàlida (tenim monedes de 0.01, és només per prevenció)
      if (validDenom.isEmpty()) break;

      // Moneda o bitllet a l'atzar
      double chosenDenom = validDenom.get(random.nextInt(validDenom.size()));
      long chosenCent = Math.round(chosenDenom * 100);

      result.put(chosenDenom, result.getOrDefault(chosenDenom, 0) + 1);

      remaining -= chosenCent;
    }
    return result;
  }
}
