package solution;

import solution.integrators.Integrator;
import solution.plotters.Plotter;

public class NBodySimulator {

  /* ATTRIBUTES */
  private Universe universe;
  private Plotter plotter;        // ENTREGA 2: com es mostra el resultat
  private Integrator integrator;  // ENTREGA 2: com es mouen els cossos

  /* METHODS */
  public NBodySimulator(Universe universe, Plotter plotter, Integrator integrator) {
    this.universe = universe;
    this.plotter = plotter;
    this.integrator = integrator;
  }

  // simulació des de initTime fins a endTime
  public void simulate(double initTime, double endTime) {
    plotter.start();
    plotter.draw(universe.getAllBodiesPosition()); // posicions inicials
    for (double t = initTime; t < endTime; t += integrator.getTimeStep()) {
      universe.update(integrator);                   // movem els cossos
      plotter.draw(universe.getAllBodiesPosition()); // els mostrem
    }
    plotter.stop();
  }

  // simulació infinita (per a online)
  public void simulate() {
    simulate(0, Double.POSITIVE_INFINITY);
  }
}