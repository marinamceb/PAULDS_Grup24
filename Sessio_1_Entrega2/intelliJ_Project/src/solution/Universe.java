//1-REFACTORIZATION: 6. Add a new class Universe with File New Java class

package solution;
import solution.integrators.Integrator;
public class Universe {

  // ATTRIBUTES

  // 1-REFACTORIZATION: 10. Add the three private attributes of the design plus the (also private)
  // attribute universe that implements the composition
  private int numBodies;
  private double radius;
  private Body[] bodies;

  // METHODS

  // 1-REFACTORIZATION: 6. Copy the constructor
  /*
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
  */

  // 2- SPECIAL BODY CONFIGURATIONS
  public Universe (Body[] b, double r){
    bodies = b;
    radius = r;
    numBodies = bodies.length;
  }

  // ENTREGA 2: força total que fan tots els altres cossos sobre el cos i
  public Vector computeForceOn(int i) {
    Vector f = new Vector(new double[2]); // comencem amb força (0,0)
    for (int j = 0; j < numBodies; j++) {
      if (i != j) {
        f = f.plus(bodies[i].forceFrom(bodies[j]));
        //bodies[i].forceFrom(bodies[j]) calcula la força que fa el cos j sobre el cos i
        //f.plus suma aquesta força a la total
      }
    }
    return f;
  }

  // 1-REFACTORIZATION: 8. Add the method void update(double dt)
  // ENTREGA 2: l'univers es mou segons l'integrador que li passen
  public void update(Integrator integrator) {
    integrator.move(this); // this = aquest univers
  }

  // 1-REFACTORIZATION: 15. Add the missing getRadius() in Universe needed by createCanvas()
  public double getRadius() {
    return radius;
  }

  public int getNumBodies() { return numBodies; }

  // ENTREGA 2: getters d'un cos concret (deleguen al Body)
  public double getBodyMass(int i)         { return bodies[i].getMass(); }         // massa
  public Vector getBodyPosition(int i)     { return bodies[i].getPosition(); }     // posició
  public Vector getBodyVelocity(int i)     { return bodies[i].getVelocity(); }     // velocitat
  public Vector getBodyAcceleration(int i) { return bodies[i].getAcceleration(); } // acceleració

  // ENTREGA 2: setters d'un cos concret (deleguen al Body)
  public void setBodyPosition(int i, Vector pos)     { bodies[i].setPosition(pos); }     // canvia la posició
  public void setBodyVelocity(int i, Vector vel)     { bodies[i].setVelocity(vel); }     // canvia la velocitat
  public void setBodyAcceleration(int i, Vector acc) { bodies[i].setAcceleration(acc); } // canvia l'acceleració

}