import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/*
* CLASSE UNIVERSE: Crea i conté els cossos de l'univers.
* */

public class Universe {
  /* ATTRIBUTES */

  private int numBodies;
  private double radius;
  private Body[] bodies;

  /* METHODS */

  public Universe(String fname){
    try{
      Scanner in=new Scanner(new FileReader(fname));
      numBodies=Integer.parseInt(in.next());
      radius=Double.parseDouble(in.next());
      bodies=new Body[numBodies];
      for(int i=0;i<numBodies;i++){
        double rx = Double.parseDouble(in.next());
        double ry = Double.parseDouble(in.next());
        double vx = Double.parseDouble(in.next());
        double vy = Double.parseDouble(in.next());
        double mass = Double.parseDouble(in.next());
        double[] position = {rx,ry};
        double[] velocity = {vx,vy};
        Vector r = new Vector(position);
        Vector v = new Vector(velocity);
        bodies[i] = new Body(r,v,mass);
        System.out.println(bodies[i]);
      }
    } catch (FileNotFoundException e) {e.printStackTrace(); }
  }

  public void update(double dt) {

  }

  public double getRadius() {
    return radius;
  }

}





