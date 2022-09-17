import codedraw.CodeDraw;

/**
 * Represents the Barnes-Hut-Tree for the simulation.
 */
public class BarnesHutTree {
	/** Ratio between the size of the octant and the distance to the other body. */
	private final double Theta = 0.5;

	/** The representation of all inner bodies. */
	private Body body;

	/** The size of this tree. */
	private final Octant boundary;

	/** The child trees of this tree. */
	private final BarnesHutTree[] octants = new BarnesHutTree[8];

	/** Indicates that this tree has no childs. */
	private boolean isExternal = true;


	public BarnesHutTree(Octant o) {
		this.boundary = o;
	}

	/**
	 * Inserts the given body to this tree.
	 */
	public void insert(Body b) {
		if (this.body == null) {
			this.body = b;
			return;
		}

		if (!isExternal) {
			this.body = this.body.merge(b);
			putBody(b);
		} else {
			for (int i = 0; i < 8; i++) {
				this.octants[i] = new BarnesHutTree(boundary.getOctant(i));
			}
			putBody(this.body);
			putBody(b);

			this.body = this.body.merge(b);
			this.isExternal = false;
		}
	}

	/**
	 * Put the body in one of the child trees.
	 */
	private void putBody(Body b) {
		for (int i = 0; i < 8; i++) {
			if (b.in(this.boundary.getOctant(i))) {
				this.octants[i].insert(b);
				return;
			}
		}
	}

	/**
	 * Updates the force of all child trees.
	 */
	public void updateForce(Body b, Vector3 force) {
		if (this.body == null || b.equals(this.body)) {
			return;
		}

		if (isExternal || (this.boundary.length() / this.body.distanceTo(b)) < this.Theta) {
			force.add(b.gravitationalForce(this.body));
		} else {
			for (int i = 0; i < 8; i++) {
				this.octants[i].updateForce(b, force);
			}
		}
	}

	/**
	 * Draw the outline of all octants.
	 */
	public void drawBoundaries(CodeDraw cd) {
		if (!isExternal) {
			for (int i = 0; i < 8; i++) {
				this.octants[i].drawBoundaries(cd);
			}
		}

		if (this.body != null && isExternal) {
			cd.setColor(SpaceDraw.massToColor(this.body.mass()));
			this.boundary.draw(cd);
		}
	}
}

