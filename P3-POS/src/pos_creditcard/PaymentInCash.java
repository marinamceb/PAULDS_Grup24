package pos_creditcard;

import change_making.CashBox;
import change_making.ChangeMaker;
import change_making.GreedyChangeMaker;
import change_making.RandomChangeMaker;

import java.util.Map;

public class PaymentInCash extends Payment {
  private final Map<Double, Integer> moneyHanded;
  private final ChangeMaker changeMaker;
  private final CashBox cashBox;
  private final double amountHanded;
  private Map<Double, Integer> computedChange;
  private boolean paymentAccepted = false;

  public PaymentInCash(Map<Double, Integer> moneyHanded, double amountToPay,
                       String changeMaking, CashBox cashBox) {
    super(amountToPay);
    this.moneyHanded = moneyHanded;
    this.cashBox = cashBox;
    this.amountHanded = totalOf(moneyHanded);

    // Strategy pattern: choose how the change will be computed
    if ("random".equals(changeMaking)) {
      changeMaker = new RandomChangeMaker();
      System.out.println("Make change with random change maker");
    } else {
      changeMaker = new GreedyChangeMaker();
      System.out.println("Make change with greedy change maker");
    }

    processPayment();
  }

  // Sums the value of the money in cents to avoid floating point errors
  private static double totalOf(Map<Double, Integer> money) {
    long cents = 0;
    for (Map.Entry<Double, Integer> entry : money.entrySet()) {
      cents += Math.round(entry.getKey() * 100) * entry.getValue();
    }
    return cents / 100.0;
  }

  private void processPayment() {
    System.out.println("money handed");
    for (Map.Entry<Double, Integer> entry : moneyHanded.entrySet()) {
      System.out.println(entry.getValue() + " of " + entry.getKey());
    }

    // The money handed by the customer goes into the cash box
    cashBox.addHandedMoney(moneyHanded);

    computedChange = change();

    // If the computed change is not in the cash box, no alternative is tried:
    // the customer's money is taken out again and the payment is rejected
    if (cashBox.canGiveChange(computedChange)) {
      cashBox.giveChange(computedChange);
      paymentAccepted = true;
    } else {
      System.out.println("Error: No es disposa del canvi necessari");
      cashBox.removeHandedMoney(moneyHanded);
      paymentAccepted = false;
    }
  }

  private Map<Double, Integer> change() {
    return changeMaker.change(amountHanded - amountToPay);
  }

  @Override
  public void print() {
    if (!paymentAccepted) {
      System.out.println("El pagament ha estat rebutjat");
      return;
    }

    // Rounded to cents so values like 11.2 are printed without floating point noise
    double totalToPay = Math.round(amountToPay * 100) / 100.0;
    double changeTotal = Math.round((amountHanded - amountToPay) * 100) / 100.0;
    System.out.println("total to pay " + totalToPay + ", change to give " + changeTotal);

    if (computedChange.isEmpty()) {
      System.out.println("no change to give");
      return;
    }
    System.out.println("the change is");
    for (Map.Entry<Double, Integer> entry : computedChange.entrySet()) {
      System.out.println(entry.getValue() + " of " + entry.getKey());
    }
  }

  public boolean isPaymentAccepted() {
    return paymentAccepted;
  }
}