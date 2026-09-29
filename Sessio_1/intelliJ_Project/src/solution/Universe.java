//1-REFACTORIZATION: 6. Add a new class Universe with File New Java class

package solution;

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

  // 1-REFACTORIZATION: 8. Add the method void update(double dt) , empty for the moment
  public void update(double dt) {
    //hem de calcular la força total sobre cada cos, sumant la força que fa cada un.
    //Moure cada cos amb aquesta força.

    Vector[] f = new Vector[numBodies]; //definim un vector que té el nombre de cossos de l'espai.
    for (int i = 0; i < numBodies; i++){ //recorrem cada cos i definim que tinguin força 0.
      f[i] = new Vector(new double[2]);
    }
    //força total sobre cada cos = suma de les forces de tots els altres
    for (int i = 0; i < numBodies; i++) {
      for (int j = 0; j < numBodies; j++) {
        if (i != j) {
          f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
          //bodies[i].forceForm(bodies[j]) calcula la força que fa el cos j, és un objecte Vector.
          //f[i].plus plus és un metode de Vector que suma dos vectors
        }
      }
    }
    //ara movem els cossos
    for (int i = 0; i < numBodies; i++) {
      bodies[i].move(f[i], dt);
    }
  }

  // 1-REFACTORIZATION: 15. Add the missing getRadius() in Universe needed by createCanvas()
  public double getRadius() {
    return radius;
  }

  public int getNumBodies() { return numBodies; }

  public Body[] getBodies() { return bodies; }

  public Vector getBodyPosition(int i){
    return getBodies()[i].getPosition();
  }

}





