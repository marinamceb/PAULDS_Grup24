package change_making;

import java.util.LinkedHashMap;
import java.util.Map;

public class GreedyChangeMaker extends ChangeMaker{
  @Override
  public Map<Double, Integer> change(double changeAmount){
    Map<Double, Integer> result = new LinkedHashMap<>();

    //Multipliquem x100 per treballar en centims i evitar possibles errors
    long remaining = Math.round(changeAmount * 100);

    // Es recorre l'array de denominacions de major a menor
    for (int i = denominations.length -1; i >= 0; i--) {
      long denomCents = Math.round(denominations[i] * 100);

      if (remaining >= denomCents){
        // Es divideix el restant entre la denominació per saber la quantitat de monedes
        int count = (int) (remaining / denomCents);
        result.put(denominations[i], count);
        remaining %= denomCents;
      }
    }
    return result;
  }
}
