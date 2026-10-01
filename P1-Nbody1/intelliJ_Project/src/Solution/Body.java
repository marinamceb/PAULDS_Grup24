package Solution;

/******************************************************************************
 *  Compilation:  javac Body.java
 *  Execution:    java Body
 *  Dependencies: Vector.java StdDraw.java
 *
 *  Implementation of a 2D Body with a position, velocity and mass.
 *
 *
 ******************************************************************************/

public class Body {
    private Vector position;           // position
    private Vector velocity;           // velocity
    private final double mass;  // mass
    private double G;

    public Body(Vector position, Vector velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.G = 6.67e-11; // 2- SPECIAL BODY CONFIGURATIONS
    }

    // 2- SPECIAL BODY CONFIGURATIONS
    // Constructor amb Gravetat
    public Body(Vector position, Vector velocity, double mass, double G) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.G = G;
    }

    public void move(Vector bodies_vector, double timeStep) {
        Vector a = bodies_vector.scale(1/mass);
        velocity = velocity.plus(a.scale(timeStep));
        position = position.plus(velocity.scale(timeStep));
    }

    public Vector forceFrom(Body b) {
        Body a = this;
        double G = 6.67e-11;
        Vector delta = b.position.minus(a.position);
        double dist = delta.magnitude();
        double magnitude = (G * a.mass * b.mass) / (dist * dist);
        return delta.direction().scale(magnitude);
    }

/*
    // 1-REFACTORIZATION: 5. According to the new design, remove the two draw methods in Body
    public void draw() {
        StdDraw.setPenRadius(0.025);
        StdDraw.point(r.cartesian(0), r.cartesian(1));
    }

    // this method is only needed if you want to change the size of the bodies
    public void draw(double penRadius) {
        StdDraw.setPenRadius(penRadius);
        StdDraw.point(r.cartesian(0), r.cartesian(1));
    }
*/

    public Vector getPosition(){ return position; }

    @Override
    public String toString() {
        return "position "+ position.toString()+", velocity "+ velocity.toString() + ", mass "+mass;
    }
}
