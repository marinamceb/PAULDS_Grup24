package solution.plotters;

import solution.utils.StdDraw;
import solution.utils.Vector;

public class Online implements Plotter {

    private boolean trace;               // dibuixar la traça o no
    private double radius;               // mida de l'univers (per a l'escala)
    private int pauseTime;               // ms d'espera entre dibuixos
    private Vector[] previousPositions;  // posicions del dibuix anterior (per a la traça)

    public Online(boolean trace, double radius, int pauseTime) {
        this.trace = trace;
        this.radius = radius;
        this.pauseTime = pauseTime;
        this.previousPositions = null;     // encara no hem dibuixat res
    }

    @Override
    public void start() {
        createCanvas();
    }

    @Override
    public void draw(Vector[] bodiesPosition) {
        if (trace) {
            // esborrem els punts anteriors pintant-los de blanc (queda la traça)
            if (previousPositions != null) {
                StdDraw.setPenColor(StdDraw.WHITE);
                drawBodies(previousPositions);
            }
            // dibuixem els punts nous en negre
            StdDraw.setPenColor(StdDraw.BLACK);
            drawBodies(bodiesPosition);
        } else {
            StdDraw.clear();
            drawBodies(bodiesPosition);
        }
        StdDraw.show();              // mostra el que ha dibuixat
        StdDraw.pause(pauseTime);    // espera uns ms
        previousPositions = bodiesPosition; // les guardem per al proper dibuix
    }

    @Override
    public void stop() {
        // online no cal fer res en acabar
    }

    private void createCanvas() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.025);
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);
        if (trace) {
            StdDraw.clear(StdDraw.GRAY); // fons gris com a les diapositives
        }
    }

    private void drawBodies(Vector[] bodiesPosition) {
        for (Vector position : bodiesPosition) {
            StdDraw.point(position.cartesian(0), position.cartesian(1));
        }
    }
}