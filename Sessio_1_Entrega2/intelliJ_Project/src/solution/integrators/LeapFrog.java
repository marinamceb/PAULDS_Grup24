package solution.integrators;

import solution.Universe;
import solution.Vector;

public class LeapFrog extends Integrator {

    public LeapFrog(double timeStep) {
        super(timeStep); // el constructor del pare guarda el timeStep
    }

    @Override
    public void move(Universe universe) {
        int numBodies = universe.getNumBodies();
        Vector[] xNew = new Vector[numBodies]; // posicions noves
        Vector[] acc = new Vector[numBodies];  // acceleracions actuals

        // FASE 1: calculem les posicions noves amb les posicions velles
        // x = x + v·Δt + ½·a·Δt²
        for (int i = 0; i < numBodies; i++) {
            Vector a = universe.computeForceOn(i).scale(1.0 / universe.getBodyMass(i)); // a = F/m
            Vector x = universe.getBodyPosition(i);
            Vector v = universe.getBodyVelocity(i);
            xNew[i] = (x.plus(v.scale(timeStep))).plus((a.scale(timeStep * timeStep)).scale(0.5));
            acc[i] = a;
        }

        // FASE 2: movem tots els cossos alhora i guardem l'acceleració vella
        for (int i = 0; i < numBodies; i++) {
            universe.setBodyPosition(i, xNew[i]);
            universe.setBodyAcceleration(i, acc[i]);
        }

        // FASE 3: amb les posicions noves calculem l'acceleració nova
        // i la velocitat amb la mitjana de les dues acceleracions
        // v = v + ½·(a_vella + a_nova)·Δt
        for (int i = 0; i < numBodies; i++) {
            Vector aNew = universe.computeForceOn(i).scale(1.0 / universe.getBodyMass(i));
            Vector v = universe.getBodyVelocity(i);
            Vector vNew = v.plus((universe.getBodyAcceleration(i).plus(aNew)).scale(0.5).scale(timeStep));
            universe.setBodyVelocity(i, vNew);
            universe.setBodyAcceleration(i, aNew); // l'acceleració nova passa a ser l'actual
        }
    }
}