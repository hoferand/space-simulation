import codedraw.CodeDraw;

import java.awt.*;
import java.util.Random;


public class Simulation {

	/** Gravitational constant. */
	public static final double G = 6.6743e-11;

	/** Astronomical unit (AU), it is the average distance of earth to the sun. [meter] */
	public static final double AU = 150e9;

	/** Mass of the sun. [kilogram] */
	public static final double SUN_MASS = 1.989e30;

	/** Radius of the sun. [meter] */
	public static final double SUN_RADIUS = 696340e3;

	/** Constants for the Simulation. */
	public static final double SECTION_SIZE = 5 * AU;
	public static final int NUMBER_OF_BODIES = 1000;
	public static final double OVERALL_SYSTEM_MASS = NUMBER_OF_BODIES * SUN_MASS;


	public static void main(String[] args) {
		CodeDraw cd = new CodeDraw();
		Body[] bodies = new Body[NUMBER_OF_BODIES];
		Vector3[] forces = new Vector3[NUMBER_OF_BODIES];
		Random random = new Random(2022);

		// Initializes the bodies.
		for (int i = 0; i < bodies.length; i++) {
			bodies[i] = new Body(
							Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / bodies.length,
							new Vector3(0.5 * random.nextGaussian() * AU, 0.5 * random.nextGaussian() * AU, 0.5 * random.nextGaussian() * AU),
							new Vector3(0 + random.nextGaussian() * 2e8, 0 + random.nextGaussian() * 2e8, 0 + random.nextGaussian() * 2e8)
			);
		}


		Octant octant = new Octant(0, 0, 0, SECTION_SIZE);
		BarnesHutTree tree;
		Vector3 force;
		boolean loop = true;
		long time = 0;
		while (loop) {
			tree = new BarnesHutTree(octant);
			loop = false;

			// Inserts all bodies to the tree.
			for (Body body : bodies) {
				if (body.in(octant)) {
					loop = true;
					tree.insert(body);
				}
			}

			// Calculates all forces.
			for (int i = 0; i < NUMBER_OF_BODIES; i++) {
				Body body = bodies[i];
				if (body.in(octant)) {
					force = new Vector3(0, 0, 0);
					tree.updateForce(body, force);
					forces[i] = force;
				}
			}

			// Moves all bodies.
			for (int i = 0; i < NUMBER_OF_BODIES; i++) {
				bodies[i].move(forces[i]);
			}

			// Show the current state every 5 cycles.
			if (time % 5 == 0) {
				cd.clear(Color.BLACK);
				for (Body body : bodies) {
					if (body.in(octant)) {
						body.draw(cd);
					}
				}
				// Draws the boundaries of every octant.
				tree.drawBoundaries(cd);
				cd.show();
			}

			time++;
		}
	}
}
