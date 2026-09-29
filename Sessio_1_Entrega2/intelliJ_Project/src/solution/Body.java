package solution;

import solution.utils.Vector;

public class Body {
    private Vector r;           //posició
    private Vector v;           //velocitat
    private Vector a;           //acceleració
    private final double mass;  //massa
    private double G;           //constant gravitatòria

    public Body(Vector r, Vector v, double mass) {
        this.r = r;
        this.v = v;
        this.a = new Vector(new double[2]); //acceleració inicial (0,0)
        this.mass = mass;
        this.G = 6.67e-11; //configuració del cos
    }

    // 2- SPECIAL BODY CONFIGURATIONS
    // Constructor amb Gravetat
    public Body(Vector r, Vector v, double mass, double G) {
        this.r = r;
        this.v = v;
        this.a = new Vector(new double[2]); //acceleració inicial (0,0)
        this.mass = mass;
        this.G = G;
    }

    public Vector forceFrom(Body b) {
        Body a = this;
        Vector delta = b.r.minus(a.r);
        double dist = delta.magnitude();
        double magnitude = (G * a.mass * b.mass) / (dist * dist);
        return delta.direction().scale(magnitude);
    }

    @Override
    public String toString() {
        return "position "+r.toString()+", velocity "+v.toString() + ", mass "+mass;
    }

    // getters
    public Vector getPosition()     { return r; }     //posició
    public Vector getVelocity()     { return v; }     //velocitat
    public Vector getAcceleration() { return a; }     //acceleració
    public double getMass()         { return mass; }  //massa

    // setters
    public void setPosition(Vector r)     { this.r = r; }  //canvia la posició
    public void setVelocity(Vector v)     { this.v = v; }  //canvia la velocitat
    public void setAcceleration(Vector a) { this.a = a; }  //canvia l'acceleració
}