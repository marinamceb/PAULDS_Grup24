package pos_creditcard;

import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static void testGreedy() {
        System.out.println("TEST: GREEDY");
        System.out.println("================");

        PointOfSale pointOfSale = new PointOfSale();
        int idSale = pointOfSale.makeNewSale("greedy");

        pointOfSale.addLineItemToSale(idSale, "Moritz", 4);
        pointOfSale.addLineItemToSale(idSale, "Coca-cola", 1);
        pointOfSale.addLineItemToSale(idSale, "Coca-cola", 2);
        pointOfSale.printReceiptOfSale(idSale);

        Map<Double, Integer> moneyHanded = new TreeMap<>();
        moneyHanded.put(10.0, 2);

        pointOfSale.payOneSaleCash(idSale, moneyHanded);
        pointOfSale.printPayment(idSale);
        System.out.println();
    }

    private static void testRandom() {
        System.out.println("TEST: RANDOM");
        System.out.println("================");

        PointOfSale pointOfSale = new PointOfSale();
        int idSale = pointOfSale.makeNewSale("random");

        pointOfSale.addLineItemToSale(idSale, "Moritz", 4);
        pointOfSale.addLineItemToSale(idSale, "Coca-cola", 1);
        pointOfSale.addLineItemToSale(idSale, "Coca-cola", 2);
        pointOfSale.printReceiptOfSale(idSale);

        Map<Double, Integer> moneyHanded = new TreeMap<>();
        moneyHanded.put(10.0, 2);

        pointOfSale.payOneSaleCash(idSale, moneyHanded);
        pointOfSale.printPayment(idSale);
        System.out.println();
    }

    private static void testNoChangeAvailable() {
        System.out.println("TEST: NO CHANGE AVAILABLE");
        System.out.println("================");

        PointOfSale pointOfSale = new PointOfSale();

        // Each payment needs 1 note of 20, 10 and 5 as change, and the box only
        // has 2 of each, so the third payment cannot be given its change
        for (int i = 0; i < 3; i++) {
            int idSale = pointOfSale.makeNewSale("greedy");

            pointOfSale.addLineItemToSale(idSale, "Moritz", 4);
            pointOfSale.addLineItemToSale(idSale, "Coca-cola", 3);
            pointOfSale.printReceiptOfSale(idSale);

            Map<Double, Integer> moneyHanded = new TreeMap<>();
            moneyHanded.put(50.0, 1);

            pointOfSale.payOneSaleCash(idSale, moneyHanded);
            pointOfSale.printPayment(idSale);
            System.out.println();
        }
    }

    private static void testExactAmount() {
        System.out.println("TEST: AMOUNT HANDED EQUALS SALE TOTAL");
        System.out.println("================");

        PointOfSale pointOfSale = new PointOfSale();
        int idSale = pointOfSale.makeNewSale("greedy");

        pointOfSale.addLineItemToSale(idSale, "Moritz", 4);
        pointOfSale.addLineItemToSale(idSale, "Coca-cola", 3);
        pointOfSale.printReceiptOfSale(idSale);

        // 10 + 1 + 0.2 = 11.20
        Map<Double, Integer> moneyHanded = new TreeMap<>();
        moneyHanded.put(10.0, 1);
        moneyHanded.put(1.0, 1);
        moneyHanded.put(0.2, 1);

        pointOfSale.payOneSaleCash(idSale, moneyHanded);
        pointOfSale.printPayment(idSale);
        System.out.println();
    }

    private static void testNotEnoughMoney() {
        System.out.println("TEST: AMOUNT HANDED LESS THAN SALE TOTAL");
        System.out.println("================");

        PointOfSale pointOfSale = new PointOfSale();
        int idSale = pointOfSale.makeNewSale("greedy");

        pointOfSale.addLineItemToSale(idSale, "Moritz", 4);
        pointOfSale.addLineItemToSale(idSale, "Coca-cola", 3);
        pointOfSale.printReceiptOfSale(idSale);

        Map<Double, Integer> moneyHanded = new TreeMap<>();
        moneyHanded.put(10.0, 1);

        pointOfSale.payOneSaleCash(idSale, moneyHanded);
        pointOfSale.printPayment(idSale);
        System.out.println();
    }

    public static void main(String[] args) {
        testGreedy();
        testRandom();
        testNoChangeAvailable();
        testExactAmount();
        testNotEnoughMoney();
    }
}