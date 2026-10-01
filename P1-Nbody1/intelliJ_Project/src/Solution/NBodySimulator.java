package Solution;

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
  public NBodySimulator(Universe universe, double timeStep, int pt, boolean doTrace) {
    this.universe = universe;
    this.timeStep = timeStep;
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
    // TODO : get the position (a Vector) of each body in universe
    // and plot it with StdDraw.point(x,y). A Vector v returns its coordinates
    // with v.cartesian(0) and v.cartesian(1)
    // For this we need to ask to a Universe its number of bodies n
    // and the position of the i-th body, i=0...n-1. Also, a Body
    // must have a getPosition() method

    //preguntar quants cossos té l'univers
    int cossos = universe.getNumBodies();
    for (int i = 0; i < cossos; i++){
      Vector posicio = universe.getBodyPosition(i);
      double x = posicio.cartesian(0);
      double y = posicio.cartesian(1);
      StdDraw.point(x, y);
    }

    // per a cada cos de 0 a n-1 demana la posició
    //un vector dona les seves coordenades amb cartesian(0) i cartesian(1)
    //pintem el punt amb StdDraw.point(x,y)

  }
}