package Solution;

// 1-REFACTORIZATION: 12. Switch to class Main . We are going to fill-in the main() with some code that gets
// the parameters for execution but only to test the creation of the Universe object
// and its bodies.
public class Main {
  public static void main(String[] args) {

    /* ==========================
    args:
    * [0] = double timeStep
    * [1] = int pauseTime
    * [2] = String trace
    * [3] = String config
    * [4] = int numBodies (si [3]=central) / int numPlanets (si [3]=planetary) /
            int nchoreography (si [3]=choreography) / fname (si [3]=file)
    * [5] = double angleVelPos (només si [3]=central)
    ========================== */

    int numargs = args.length;
    assert numargs >= 4 : "invalid number of arguments";

    double timeStep = Double.parseDouble(args[0]);
    int pauseTime = Integer.parseInt(args[1]);
    boolean trace = args[2].toLowerCase().equals("trace");

    String config = args[3].toLowerCase();
    Universe universe;

    switch(config){
      case "central":
        assert args.length >= 6 : "Falten arguments per a la configuració central (calen 6 arguments).";
        int numBodies = Integer.parseInt(args[4]);
        double angleVelPos = Double.parseDouble(args[5]);
        universe = UniverseFactory.createCentralConfiguration(numBodies, angleVelPos);
        break;
      case "planetary":
        assert args.length >= 5 : "Falten arguments per a la configuració planetària (calen 5 arguments).";
        int numPlanets = Integer.parseInt(args[4]);
        universe = UniverseFactory.createPlanetaryConfiguration(numPlanets);
        break;
      case "choreography":
        assert args.length >= 5 : "Falten arguments per a la configuració de coreografia (calen 5 arguments).";
        int nchoreography = Integer.parseInt(args[4]);
        universe = UniverseFactory.createChoreography(nchoreography);
        break;
      case "file":
        assert args.length >= 5 : "Cal especificar la ruta del fitxer a args[4].";
        String fname = args[4];
        universe = UniverseFactory.createFromFile(fname);
        break;
      default:
        universe = UniverseFactory.createFromFile(args[3]);
        break;
    }

    NBodySimulator simulator = new NBodySimulator(universe, timeStep, pauseTime, trace);
    simulator.simulate();
  }
}