import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

// 2- SPECIAL BODY CONFIGURATIONS

public class UniverseFactory {

  // CONSTRUCTOR
  public static Universe createFromFile(String fname) {
    try {
      Scanner in = new Scanner(new FileReader(fname));
      int numBodies = Integer.parseInt(in.next());
      double radius = Double.parseDouble(in.next());
      Body[] bodies = new Body[numBodies];
      for (int i = 0; i < numBodies; i++) {
        double rx = Double.parseDouble(in.next());
        double ry = Double.parseDouble(in.next());
        double vx = Double.parseDouble(in.next());
        double vy = Double.parseDouble(in.next());
        double mass = Double.parseDouble(in.next());
        double[] position = {rx, ry};
        double[] velocity = {vx, vy};
        Vector r = new Vector(position);
        Vector v = new Vector(velocity);
        bodies[i] = new Body(r, v, mass);
        System.out.println(bodies[i]);
      }
      in.close();
      return new Universe(bodies, radius);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  // CONFIGURACIÓ CENTRAL
  public static Universe createCentralConfiguration(int numBodies, double angleVelPos) {
    final double RADIUS = 1e11;
    final double DISTANCE = 0.4 * RADIUS;
    final double MASS = 1e33;
    final double GAMMA = 5e-5;
    double velocityMagnitude = GAMMA * DISTANCE;
    Body[] bodies = new Body[numBodies];

    for (int i = 0; i < numBodies; i++) {
      double anglePos = (2 * Math.PI * i) / numBodies;
      double rx = DISTANCE * Math.cos(anglePos);
      double ry = DISTANCE * Math.sin(anglePos);
      double vx = velocityMagnitude * Math.cos(anglePos + angleVelPos);
      double vy = velocityMagnitude * Math.sin(anglePos + angleVelPos);
      bodies[i] = new Body(new Vector(new double[]{rx, ry}),
          new Vector(new double[]{vx, vy}), MASS);
    }

    return new Universe(bodies, RADIUS);
  }

  // RANDOM BETWEEN
  private static double randomBetween(double min, double max) {
    return min + Math.random() * (max - min);
  }

  // CONFIGURACIÓ PLANETÀRIA
  public static Universe createPlanetaryConfiguration(int numPlanets) {

    final double RADIUS = 1e12;
    final double MASS = 1e39;
    final double MIN_MASS = 1e20;
    final double MAX_MASS = 1e30;
    final double MAX_VELOCITY = 1e05;
    final double MIN_VELOCITY = 1e04;

    // A l'univers hi haurà n planetes +1 (el Sol)
    int numBodies = numPlanets + 1;
    Body[] bodies = new Body[numBodies];

    // Fem que el primer element (el Sol) tingui posició (0,0) amb velocitat (0,0) i la massa del Sol
    bodies[0] = new Body(new Vector(new double[2]), new Vector(new double[2]), MASS);

    for (int i = 1; i < numBodies; i++) {
      double angle = randomBetween(-Math.PI, Math.PI);
      double rho = randomBetween(RADIUS / 4, RADIUS / 2);

      double rx = Math.cos(angle) * rho;
      double ry = Math.sin(angle) * rho;

      double vx = -ry / 1000. + randomBetween(MIN_VELOCITY, MAX_VELOCITY);
      double vy = rx / 1000. + randomBetween(MIN_VELOCITY, MAX_VELOCITY);

      double mass = randomBetween(MIN_MASS, MAX_MASS);

      double[] position = {rx, ry};
      double[] velocity = {vx, vy};
      Vector r = new Vector(position);
      Vector v = new Vector(velocity);

      bodies[i] = new Body(r, v, mass);
    }
    return new Universe(bodies, RADIUS);
  }

  // COREOGRAFIES DE 3 COSSOS
  public static Universe createChoreography(int nchoreography) {
    if (nchoreography < 1 || nchoreography > 345) {
      System.err.println("El número de coreografia ha de ser entre 1 i 345.");
      return null;
    }

    try {
      Scanner in = new Scanner(new FileReader("data/simo-initial-conditions.txt"));
      double c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0;

      // Avancem pel fitxer fins a la coreografia seleccionada
      for (int i = 1; i <= nchoreography; i++) {
        c1 = Double.parseDouble(in.next());
        c2 = Double.parseDouble(in.next());
        c3 = Double.parseDouble(in.next());
        c4 = Double.parseDouble(in.next());
        c5 = Double.parseDouble(in.next());
      }
      in.close();

      Vector r1 = new Vector(new double[]{-2 * c1, 0});
      Vector r2 = new Vector(new double[]{c1, c2});
      Vector r3 = new Vector(new double[]{c1, -c2});

      Vector v1 = new Vector(new double[]{0, -2 * c4});
      Vector v2 = new Vector(new double[]{c3, c4});
      Vector v3 = new Vector(new double[]{-c3, c4});

      double mass = 1.0 / 3.0;
      double G = 1.0;
      double radius = 0.5;

      Body[] bodies = new Body[3];
      bodies[0] = new Body(r1, v1, mass, G);
      bodies[1] = new Body(r2, v2, mass, G);
      bodies[2] = new Body(r3, v3, mass, G);

      return new Universe(bodies, radius);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }
}
