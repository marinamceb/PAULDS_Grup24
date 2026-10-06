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
    System.out.println(DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm").format(dateTime));
    for (SaleLineItem saleLineItem : saleLineItems) {
      saleLineItem.print();
    }
    System.out.printf("Total %.2f\n", total());
  }

  public void badPrintReceipt() {
    System.out.println("Sale " + id);
    System.out.println(DateTimeFormatter.ofPattern("dd-MM-yyy hh:mm").format(dateTime));
    double total = 0.;
    for (SaleLineItem saleLineItem : saleLineItems) {
      String prodName = saleLineItem.productSpecification.getName();
      int quantity = saleLineItem.quantity; //getQuantity();
      double price = saleLineItem.productSpecification.getPrice();
      double subtotal = quantity * price;
      System.out.printf("%s %d x %.2f = %.2f\n", prodName, quantity, price, subtotal);
      total += subtotal;
    }
    System.out.printf("Total %.2f\n", total);
  }

  public void payCash(Map<Double, Integer> moneyHanded, CashBox cashBox) {
    assert !isPaid : "sale " + id + " has already been paid";

    double totalHanded = 0;
    for (Map.Entry<Double, Integer> entry : moneyHanded.entrySet()) {
      totalHanded += entry.getKey() * entry.getValue();
    }

    double totalSale = total();
    if (totalHanded >= totalSale) {
      PaymentInCash cashPayment = new PaymentInCash(moneyHanded, total(), changeMaking, cashBox);
      payment = cashPayment;

      if(cashPayment.isPaymentAccepted()){
        isPaid = true;
        payment.print();
      } else{
        System.out.println("No s'ha acceptat el pagament");
      }

    } else {
      System.out.println("Amount handed " + totalHanded
          + " is not enough to pay total of sale " + totalSale);
    }
  }

  public void payCreditCard(String ccnumber) {
    assert !isPaid : "sale " + id + " has already been paid";
    payment = new PaymentCreditCard(ccnumber, total());
    if ( ((PaymentCreditCard) payment).isAuthorized() ) {
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
