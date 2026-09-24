
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
  public void simulate() {
    // TODO: to simulate, first create a canvas and then iterate forever :
    // clear the canvas with StdDraw.clear(), update the universe (which
    // means the position of its bodies), draw the universe (which means
    // the position of its bodies), do StdDraw.show(), wait for some time
    // that can be 0 with StdDraw.pause(pauseTime)

    createCanvas();

    while (true){
      StdDraw.clear();

      // "Update the universe"
      universe.update(timeStep);

      // "Draw the universe"
      drawUniverse();

      StdDraw.show();

      // "wait for some time"
      StdDraw.pause(pauseTime);
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

  }
}