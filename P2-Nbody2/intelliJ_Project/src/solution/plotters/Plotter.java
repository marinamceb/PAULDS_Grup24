package solution.plotters;

import solution.utils.Vector;

public interface Plotter {
    void start();                     // preparar-se abans de començar
    void draw(Vector[] positions);    // mostrar les posicions d'un pas
    void stop();                      // acabar
}