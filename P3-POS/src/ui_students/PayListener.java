package ui_students;

import pos_creditcard.PointOfSale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;


public class PayListener implements ActionListener {
  private TableListener listenerCurrentTable;
  private AmountListener amountListener;
  private PointOfSale pointOfSale;

  public PayListener(PointOfSale pointOfSale, AmountListener amountListener) {
    this.pointOfSale = pointOfSale;
    this.amountListener = amountListener; // has the amount paid for the sale of current table
  }

  public void setListenerCurrentTable(TableListener tableListener) {
    listenerCurrentTable = tableListener;
    // knows the id of its current sale, if any
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    System.out.println("Pressed Pay button");
    if (listenerCurrentTable != null) {
      if (listenerCurrentTable.hasASale()) {
        int idSale = listenerCurrentTable.getSaleId();
        double paidAmount = amountListener.getPaidAmount();

        if(!pointOfSale.isSalePaid(idSale) && (paidAmount > 0)){
          Map<Double, Integer> moneyHanded = convertToDenominations(paidAmount);

          pointOfSale.payOneSaleCash(idSale, moneyHanded);

          pointOfSale.printPayment(idSale);
          //paidAmount = 0;
          amountListener.setText("0.0");
          listenerCurrentTable.clearSale();
        } else {
          System.out.println("Sale of table " + listenerCurrentTable.getTableId()
              + " has already been paid");
        }
      }
    }
  }

  private Map<Double, Integer> convertToDenominations(double amount){
    Map<Double, Integer> map = new TreeMap<>();
    double[] denominations = {50.0, 20.0, 10.0, 5.0, 2.0, 1.0, 0.50, 0.20, 0.10, 0.05, 0.02, 0.01};

    long remainingCents = Math.round(amount * 100);

    for (double denom : denominations) {
      long denomCents = Math.round(denom * 100);
      if (remainingCents >= denomCents) {
        int count = (int) (remainingCents / denomCents);
        map.put(denom, count);
        remainingCents %= denomCents;
      }
    }
    return map;
  }
}
