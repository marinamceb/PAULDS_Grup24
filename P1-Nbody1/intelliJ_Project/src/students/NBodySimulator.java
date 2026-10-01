
// 1-REFACTORIZATION: 9. Add a new class NBodySimulator
public class NBodySimulator {

  /* ATTRIBUTES */

  // 1-REFACTORIZATION: 10. Add the three private attributes of the design plus the (also private) attribute
  // universe that implements the composition
  private double timeStep;
  private int pauseTime;
  private boolean trace;
  private Universe universe;

  /* METHODS */

  // 1-REFACTORIZATION: 9. Add the constructor
  public NBodySimulator(Universe universe, double dt, int pt, boolean doTrace) {
    this.universe = universe;
    timeStep = dt;
    pauseTime = pt;
    trace = doTrace;
  }

  // 1-REFACTORIZATION: 11. Add an empty method public void simulate() {}
  public void simulate() { //Neteja la pantalla, mou els cossos, els dibuixa i mostra resultat
    createCanvas();
    while (true) {
      if (trace){
        StdDraw.setPenColor(StdDraw.WHITE);
        drawUniverse();//Dibuixa els cossos
        universe.update(timeStep); //movem els cossos
        StdDraw.setPenColor(StdDraw.BLACK);
        drawUniverse();
        //sense l'update no funciona, no es moura res.
      }
      else {
        StdDraw.clear();
        universe.update(timeStep);
        drawUniverse();
      }
      StdDraw.show(); //mostra el que ha dibuixat
      StdDraw.pause(pauseTime); //espera uns ms

    }
  }

  // 1-REFACTORIZATION: 14. Back to NBodySimulation , add the following method that creates the canvas, the
  // bitmap where we will draw
  private void createCanvas() {
    //StdDraw.setCanvasSize(700, 700); // uncomment for a larger window
    StdDraw.enableDoubleBuffering();
    StdDraw.setPenRadius(0.025);
    double radius = universe.getRadius();
    // read from txt file, second line
    StdDraw.setXscale(-radius, +radius);
    StdDraw.setYscale(-radius, +radius);
  }

  // 1-REFACTORIZATION: 16. create in NBodySimulator an method drawUniverse() , empty for the moment
  // (and void, private, according to the design)
  private void drawUniverse() {
    //preguntar quants cossos té l'univers
    int cossos = universe.getNumBodies();
    for (int i = 0; i < cossos; i++){
      Vector_original posicio = universe.getBodyPosition(i);
      double x = posicio.cartesian(0);
      double y = posicio.cartesian(1);
      StdDraw.point(x, y);
    }

    // per a cada cos de 0 a n-1 demana la posició
    //un vector dona les seves coordenades amb cartesian(0) i cartesian(1)
    //pintem el punt amb StdDraw.point(x,y)

  }
}