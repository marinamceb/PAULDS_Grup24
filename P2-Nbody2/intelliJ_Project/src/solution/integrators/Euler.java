package solution.integrators;

import solution.Universe;
import solution.utils.Vector;

public class Euler extends Integrator {

    public Euler(double timeStep) {
        super(timeStep); // el constructor del pare guarda el timeStep
    }

    @Override
    public void move(Universe universe) {
        int numBodies = universe.getNumBodies();

        // primer calculem les noves posicions i velocitats de TOTS els cossos
        // sense modificar-ne cap, perquè computeForceOn faci servir les posicions velles
        Vector[] newPositions = new Vector[numBodies];
        Vector[] newVelocities = new Vector[numBodies];

        for (int i = 0; i < numBodies; i++) {
            Vector a = universe.computeForceOn(i).scale(1.0 / universe.getBodyMass(i)); // a = F/m
            newVelocities[i] = universe.getBodyVelocity(i).plus(a.scale(timeStep));     // v = v + a·Δt
            newPositions[i] = universe.getBodyPosition(i).plus(newVelocities[i].scale(timeStep)); // x = x + v·Δt
        }

        // després els actualitzem tots alhora
        for (int i = 0; i < numBodies; i++) {
            universe.setBodyPosition(i, newPositions[i]);
            universe.setBodyVelocity(i, newVelocities[i]);
            // Euler no necessita guardar l'acceleració
        }
    }
}