package solution.integrators;

import solution.Universe;

public abstract class Integrator {

    protected double timeStep; // pas de temps (Δt), comú a tots els integradors

    public Integrator(double timeStep) {
        this.timeStep = timeStep;
    }

    // mou tots els cossos de l'univers un pas de temps
    // cada integrador ho fa a la seva manera
    public abstract void move(Universe universe);
}