package solution;

import solution.integrators.Integrator;

public class NBodySimulator {

  /* ATTRIBUTES */
  private Integrator integrator; // ENTREGA 2: substitueix el timeStep
  private int pauseTime;
  private boolean trace;
  private Universe universe;

  /* METHODS */

  // ENTREGA 2: el constructor rep l'integrador en lloc de dt
  public NBodySimulator(Universe universe, Integrator integrator, int pt, boolean doTrace) {
    this.universe = universe;
    this.integrator = integrator;
    pauseTime = pt;
    trace = doTrace;
  }

  public void simulate() { //Neteja la pantalla, mou els cossos, els dibuixa i mostra resultat
    createCanvas();
    while (true) {
      if (trace){
        StdDraw.setPenColor(StdDraw.WHITE);
        drawUniverse();
        universe.update(integrator); // ENTREGA 2: movem els cossos amb l'integrador
        StdDraw.setPenColor(StdDraw.BLACK);
        drawUniverse();
      }
      else {
        StdDraw.clear();
        universe.update(integrator); // ENTREGA 2: movem els cossos amb l'integrador
        drawUniverse();
      }
      StdDraw.show(); //mostra el que ha dibuixat
      StdDraw.pause(pauseTime); //espera uns ms
    }
  }

  private void createCanvas() {
    StdDraw.enableDoubleBuffering();
    StdDraw.setPenRadius(0.025);
    double radius = universe.getRadius();
    StdDraw.setXscale(-radius, +radius);
    StdDraw.setYscale(-radius, +radius);
  }

  private void drawUniverse() {
    int cossos = universe.getNumBodies();
    for (int i = 0; i < cossos; i++){
      Vector posicio = universe.getBodyPosition(i);
      StdDraw.point(posicio.cartesian(0), posicio.cartesian(1));
    }
  }
}