package solution;

import solution.integrators.Euler;
import solution.integrators.Integrator;
import solution.integrators.LeapFrog;

public class Main {

  public static void main(String[] args) {

    /* ==========================
    args:
    * [0] = double dt
    * [1] = int pauseTime
    * [2] = String trace
    * [3] = String integrator (euler / leapfrog)
    * [4] = String config
    * [5] = int numBodies (si [4]=central) / int numPlanets (si [4]=planetary) /
            int nchoreography (si [4]=choreography) / fname (si [4]=file)
    * [6] = double angleVelPos (només si [4]=central)
    ========================== */

    int numargs = args.length;
    assert numargs >= 5 : "invalid number of arguments";

    double dt = Double.parseDouble(args[0]);
    int pauseTime = Integer.parseInt(args[1]);
    boolean trace = args[2].toLowerCase().equals("trace");

    // ENTREGA 2: creem l'integrador segons l'argument
    String integratorName = args[3].toLowerCase();
    Integrator integrator;
    switch (integratorName) {
      case "euler":
        integrator = new Euler(dt);
        break;
      case "leapfrog":
        integrator = new LeapFrog(dt);
        break;
      default:
        throw new IllegalArgumentException("Integrador desconegut: " + args[3]);
    }

    String config = args[4].toLowerCase();
    Universe universe;

    switch(config){
      case "central":
        assert args.length >= 7 : "Falten arguments per a la configuració central (calen 7 arguments).";
        int numBodies = Integer.parseInt(args[5]);
        double angleVelPos = Double.parseDouble(args[6]);
        universe = UniverseFactory.createCentralConfiguration(numBodies, angleVelPos);
        break;
      case "planetary":
        assert args.length >= 6 : "Falten arguments per a la configuració planetària (calen 6 arguments).";
        int numPlanets = Integer.parseInt(args[5]);
        universe = UniverseFactory.createPlanetaryConfiguration(numPlanets);
        break;
      case "choreography":
        assert args.length >= 6 : "Falten arguments per a la configuració de coreografia (calen 6 arguments).";
        int nchoreography = Integer.parseInt(args[5]);
        universe = UniverseFactory.createChoreography(nchoreography);
        break;
      case "file":
        assert args.length >= 6 : "Cal especificar la ruta del fitxer a args[5].";
        String fname = args[5];
        universe = UniverseFactory.createFromFile(fname);
        break;
      default:
        universe = UniverseFactory.createFromFile(args[4]);
        break;
    }

    NBodySimulator simulator = new NBodySimulator(universe, integrator, pauseTime, trace);
    simulator.simulate();
  }
}

