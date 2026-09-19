import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

//1-REFACTORIZATION: 6. Add a new class Universe with File New Java class


public class Universe {

  // ATTRIBUTES

  // 1-REFACTORIZATION: 10. Add the three private attributes of the design plus the (also private)
  // attribute universe that implements the composition
  private int numBodies;
  private double radius;
  private Body[] bodies;

  // METHODS

  // 1-REFACTORIZATION: 6. Copy the constructor
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

  // 1-REFACTORIZATION: 8. Add the method void update(double dt) , empty for the moment
  public void update(double dt) {

  }

  // 1-REFACTORIZATION: 15. Add the missing getRadius() in Universe needed by createCanvas()
  public double getRadius() {
    return radius;
  }

}





