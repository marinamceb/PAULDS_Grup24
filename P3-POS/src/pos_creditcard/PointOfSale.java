package pos_creditcard;

import change_making.CashBox;

import java.util.ArrayList;
import java.util.Map;


public class PointOfSale {
  private ProductCatalog productCatalog;
  private ArrayList<Sale> sales;
  private int idLastSale = 0;
  private final String FILE_NAME = "src/pos_creditcard/catalog.txt";
  private CashBox cashBox;

  public PointOfSale() {
    productCatalog = new ProductCatalog(FILE_NAME);
    sales = new ArrayList<>();

    cashBox = new CashBox();
    double[] startingDenoms = {0.01, 0.02, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 20.0};
    for (double d : startingDenoms) {
      cashBox.addCoins(d, 5);
    }
  }

  public int makeNewSale(String changeMaking) {
    idLastSale++;
    Sale newSale = new Sale(idLastSale, changeMaking);
    sales.add(newSale);
    return idLastSale;
  }

  public void addLineItemToSale(int idSale, String productName, int quantity) {
    ProductSpecification productSpecification = productCatalog.searchByName(productName);
    Sale sale = searchSaleById(idSale);
    sale.addLineItem(productSpecification, quantity);
  }

  private Sale searchSaleById(int id) {
    for (Sale s : sales) {
      if (s.getId() == id) {
        return s;
      }
    }
    return null;
  }

  public void printReceiptOfSale(int saleId) {
    Sale sale = searchSaleById(saleId);
    sale.printReceipt();
  }

  public void payOneSaleCash(int saleId, Map<Double, Integer> moneyHanded) {
    Sale sale = searchSaleById(saleId);
    sale.payCash(moneyHanded, cashBox);
  }

  public void payOneSaleCreditCard(int saleId, String ccnumber) {
    Sale sale = searchSaleById(saleId);
    sale.payCreditCard(ccnumber);
  }

  public void printPayment(int saleId) {
    Sale sale = searchSaleById(saleId);
    sale.printPayment();
  }

  public boolean isSalePaid(int saleId) {
    return searchSaleById(saleId).isPaid();
  }

  // this is for the user interface
  public ArrayList<String> getProductNames() {
    return productCatalog.getProductNames();
  }
}

