package pos_creditcard;

import change_making.CashBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class Sale {
  private int id;
  private ArrayList<SaleLineItem> saleLineItems = new ArrayList<>();
  private LocalDateTime dateTime = LocalDateTime.now();
  private Payment payment = null;  // note : supertype
  boolean isPaid = false;
  private String changeMaking;

  public Sale(int id, String changeMaking) {
    this.id = id;
    this.changeMaking = changeMaking;
  }

  public int getId() {
    return id;
  }

  public void addLineItem(ProductSpecification productSpecification, int quantity) {
    // Unknown product (searchByName returned null): nothing to add
    if (productSpecification == null) {
      return;
    }
    for (SaleLineItem item : saleLineItems) {
      if (item.productSpecification == productSpecification) { // same object
        item.incrementQuantity(quantity);
        return;
      }
    }
    saleLineItems.add(new SaleLineItem(productSpecification, quantity));
  }

  private double total() {
    double total = 0.;
    for (SaleLineItem saleLineItem : saleLineItems) {
      total += saleLineItem.subtotal();
    }
    return total;
  }

  public void printReceipt() {
    System.out.println("Sale " + id);
    System.out.println(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(dateTime));
    for (SaleLineItem saleLineItem : saleLineItems) {
      saleLineItem.print();
    }
    System.out.printf("Total %.2f\n", total());
  }

  public void payCash(Map<Double, Integer> moneyHanded, CashBox cashBox) {
    assert !isPaid : "sale " + id + " has already been paid";

    // Both amounts are compared in cents to avoid floating point errors
    // (e.g. 7.6 + 3.6 = 11.200000000000001)
    long centsHanded = 0;
    for (Map.Entry<Double, Integer> entry : moneyHanded.entrySet()) {
      centsHanded += Math.round(entry.getKey() * 100) * entry.getValue();
    }
    long centsToPay = Math.round(total() * 100);

    if (centsHanded >= centsToPay) {
      PaymentInCash cashPayment =
              new PaymentInCash(moneyHanded, centsToPay / 100.0, changeMaking, cashBox);

      // The payment is only kept if it has been accepted. It is printed
      // later with printPayment(), so it is not shown twice
      if (cashPayment.isPaymentAccepted()) {
        payment = cashPayment;
        isPaid = true;
      } else {
        System.out.println("No s'ha acceptat el pagament");
      }
    } else {
      System.out.printf("Amount handed %.2f is not enough to pay total of sale %.2f\n",
              centsHanded / 100.0, centsToPay / 100.0);
    }
  }

  public void payCreditCard(String ccnumber) {
    assert !isPaid : "sale " + id + " has already been paid";
    payment = new PaymentCreditCard(ccnumber, total());
    if (((PaymentCreditCard) payment).isAuthorized()) {
      // note cast, necessary to call isAuthorized()
      isPaid = true;
    }
  }

  public void printPayment() {
    if (isPaid) {
      payment.print();
    } else {
      System.out.println("Sale " + id + " not paid yet");
    }
  }

  public boolean isPaid() {
    return isPaid;
  }
}