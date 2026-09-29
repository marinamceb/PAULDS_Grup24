package solution.plotters;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import solution.utils.Vector;

public class Offline implements Plotter {

    private String fname;             // fitxer on guardem les posicions
    private int numBodies;            // nombre de cossos (primera línia del fitxer)
    private PrintWriter printWriter;  // per escriure al fitxer

    public Offline(String fname, int numBodies) {
        this.fname = fname;
        this.numBodies = numBodies;
    }

    @Override
    public void start() {
        // obrim el fitxer i escrivim el nombre de cossos
        try {
            printWriter = new PrintWriter(new FileWriter(fname));
            printWriter.println(numBodies);
        } catch (IOException e) {
            throw new RuntimeException("No s'ha pogut obrir el fitxer " + fname, e);
        }
    }

    @Override
    public void draw(Vector[] bodiesPosition) {
        // escrivim una línia "x y" per a cada cos
        for (Vector position : bodiesPosition) {
            printWriter.printf(Locale.US, "%f %f\n", position.cartesian(0), position.cartesian(1));
        }
    }

    @Override
    public void stop() {
        // tanquem el fitxer (si no, les últimes dades es poden perdre)
        printWriter.close();
    }
}