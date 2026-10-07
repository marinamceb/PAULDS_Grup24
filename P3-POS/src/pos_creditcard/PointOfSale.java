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

    // Initial cash box: 5 of each coin and 2 of each note up to 20 euros
    cashBox = new CashBox();
    double[] coins = {0.01, 0.02, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0};
    double[] notes = {5.0, 10.0, 20.0};
    for (double d : coins) {
      cashBox.addCoins(d, 5);
    }
    for (double d : notes) {
      cashBox.addCoins(d, 2);
    }
  }

  public int makeNewSale(String changeMaking) {
    idLastSale++;
    Sale newSale = new Sale(idLastSale, changeMaking);
    sales.add(newSale);
    return idLastSale;
  }

  public void addLineItemToSale(int idSale, String productName, int quantity) {
    Sale sale = searchSaleById(idSale);
    if (sale == null) {
      System.out.println("Sale " + idSale + " does not exist");
      return;
    }
    ProductSpecification productSpecification = productCatalog.searchByName(productName);
    if (productSpecification == null) {
      return; // searchByName has already reported the unknown product
    }
    sale.addLineItem(productSpecification, quantity);
    System.out.println("ordered " + quantity + " " + productName);
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
    if (sale == null) {
      System.out.println("Sale " + saleId + " does not exist");
      return;
    }
    sale.printReceipt();
  }

  public void payOneSaleCash(int saleId, Map<Double, Integer> moneyHanded) {
    Sale sale = searchSaleById(saleId);
    if (sale == null) {
      System.out.println("Sale " + saleId + " does not exist");
      return;
    }
    sale.payCash(moneyHanded, cashBox);
  }

  public void payOneSaleCreditCard(int saleId, String ccnumber) {
    Sale sale = searchSaleById(saleId);
    if (sale == null) {
      System.out.println("Sale " + saleId + " does not exist");
      return;
    }
    sale.payCreditCard(ccnumber);
  }

  public void printPayment(int saleId) {
    Sale sale = searchSaleById(saleId);
    if (sale == null) {
      System.out.println("Sale " + saleId + " does not exist");
      return;
    }
    sale.printPayment();
  }

  public boolean isSalePaid(int saleId) {
    Sale sale = searchSaleById(saleId);
    return sale != null && sale.isPaid();
  }

  // Prints what is currently in the cash box
  public void printCashBox() {
    cashBox.printStatus();
  }

  // this is for the user interface
  public ArrayList<String> getProductNames() {
    return productCatalog.getProductNames();
  }
}