package solution;
import solution.plotters.Offline;
import solution.integrators.Euler;
import solution.integrators.Integrator;
import solution.integrators.LeapFrog;
import solution.plotters.Online;
import solution.plotters.Plotter;

public class Main {

  public static void main(String[] args) {

    /* ==========================
    args:
    * [0] = double dt
    * [1] = String integrator (euler / leapfrog)
    * [2] = String plotter (online / offline)
    * [3] = int pauseTime (si online) / String fname sortida (si offline)
    * [4] = String trace (si online) / double endTime (si offline)
    * [5] = String config
    * [6] = int numBodies (si central) / int numPlanets (si planetary) /
            int nchoreography (si choreography) / fname (si file)
    * [7] = double angleVelPos (només si central)
    ========================== */

    assert args.length >= 6 : "invalid number of arguments";

    double dt = Double.parseDouble(args[0]);

    // 1. INTEGRADOR
    Integrator integrator;
    switch (args[1].toLowerCase()) {
      case "euler":
        integrator = new Euler(dt);
        break;
      case "leapfrog":
        integrator = new LeapFrog(dt);
        break;
      default:
        throw new IllegalArgumentException("Integrador desconegut: " + args[1]);
    }

    // 2. UNIVERS (abans que el plotter, perquè el plotter necessita el radius)
    Universe universe;
    switch (args[5].toLowerCase()) {
      case "central":
        assert args.length >= 8 : "Falten arguments per a la configuració central.";
        int numBodies = Integer.parseInt(args[6]);
        double angleVelPos = Double.parseDouble(args[7]);
        universe = UniverseFactory.createCentralConfiguration(numBodies, angleVelPos);
        break;
      case "planetary":
        assert args.length >= 7 : "Falten arguments per a la configuració planetària.";
        int numPlanets = Integer.parseInt(args[6]);
        universe = UniverseFactory.createPlanetaryConfiguration(numPlanets);
        break;
      case "choreography":
        assert args.length >= 7 : "Falten arguments per a la coreografia.";
        int nchoreography = Integer.parseInt(args[6]);
        universe = UniverseFactory.createChoreography(nchoreography);
        break;
      case "file":
        assert args.length >= 7 : "Cal especificar la ruta del fitxer a args[6].";
        universe = UniverseFactory.createFromFile(args[6]);
        break;
      default:
        universe = UniverseFactory.createFromFile(args[5]);
        break;
    }
    // 3. PLOTTER
    Plotter plotter;
    double endTime;
    switch (args[2].toLowerCase()) {
      case "online":
        int pauseTime = Integer.parseInt(args[3]);
        boolean trace = args[4].toLowerCase().equals("trace");
        plotter = new Online(trace, universe.getRadius(), pauseTime);
        endTime = Double.POSITIVE_INFINITY; // online no s'acaba mai
        break;
      case "offline":
        String outputFile = args[3];
        endTime = Double.parseDouble(args[4]);
        plotter = new Offline(outputFile, universe.getNumBodies());
        break;
      default:
        throw new IllegalArgumentException("Plotter desconegut: " + args[2]);
    }

    // 4. SIMULACIÓ
    NBodySimulator simulator = new NBodySimulator(universe, plotter, integrator);
    simulator.simulate(0, endTime);
    System.out.println("Simulació acabada.");
  }
}