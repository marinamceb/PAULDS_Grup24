package pos_creditcard;

import change_making.CashBox;
import change_making.ChangeMaker;
import change_making.GreedyChangeMaker;
import change_making.RandomChangeMaker;

import java.util.Map;

public class PaymentInCash extends Payment {
  double amountHanded;
  private ChangeMaker changeMaker;
  private CashBox cashBox;
  private Map<Double, Integer> moneyHanded;
  private boolean paymentAccepted = false;
  private Map<Double, Integer> computedChange;

  public PaymentInCash(Map<Double, Integer> moneyHanded, double amountToPay, String changeMaking, CashBox cashBox) {
    super(amountToPay);
//    assert amountHanded >= amountToPay;
    this.moneyHanded = moneyHanded;
    this.cashBox = cashBox;

    // Es calcula el total de diners entregats
    this.amountHanded = 0;
    for (Map.Entry<Double, Integer> entry : moneyHanded.entrySet()){
      this.amountHanded += entry.getKey() * entry.getValue();
    }

    if(changeMaking != null && changeMaking.equals("random")){
      changeMaker = new RandomChangeMaker();
    } else {
      changeMaker = new GreedyChangeMaker();
    }

    processPayment();
  }

  private void processPayment(){
    // S'ingressen els diners del client
    cashBox.addHandedMoney(moneyHanded);

    // Es calcula el canvi
    computedChange = changeMaker.change(amountHanded - amountToPay);

    //Comprova la disponibilitat
    if (cashBox.canGiveChange(computedChange)){
      cashBox.giveChange(computedChange);
      paymentAccepted = true;
    } else{
      System.out.println("Error: No es disposa del canvi necessari");
      cashBox.removeHandedMoney(moneyHanded);
      paymentAccepted = false;
    }
  }

  private Map<Double, Integer> change() {
    // el parametre es el canvi a retornar
    return changeMaker.change(amountHanded - amountToPay);
  }

//  public void print() {
//    System.out.printf("\nAmount handed : %.2f\nChange : %.2f\n", amountHanded, change());
//  }
  @Override
  public void print() {
    if(!paymentAccepted){
      System.out.println("El pagament ha estat rebutjat");
      return;
    }

    double changeTotal = amountHanded - amountToPay;
    System.out.printf("total to pay %.2f, change to give %.2f\n", amountToPay, changeTotal);

    System.out.println("the change is");
    for (Map.Entry<Double, Integer> entry : computedChange.entrySet()) {
      System.out.println(entry.getValue() + " of " + entry.getKey());
    }
  }

  public boolean isPaymentAccepted(){
    return paymentAccepted;
  }
}
