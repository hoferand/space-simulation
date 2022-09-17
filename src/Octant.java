import codedraw.CodeDraw;

/**
 * Represents an Octant for the Octree.
 */
public class Octant {
	/** The x coordinate of the center of this octant. */
	private double xmid;

	/** The y coordinate of the center of this octant. */
	private double ymid;

	/** The z coordinate of the center of this octant. */
	private double zmid;

	/** The length of this octant. */
	private double length;


	public Octant(double xmid, double ymid, double zmid, double length) {
		this.xmid = xmid;
		this.ymid = ymid;
		this.zmid = zmid;
		this.length = length;
	}

	/**
	 * Returns the length of this octant.
	 */
	public double length() {
		return this.length;
	}

	/**
	 * Checks if the given point (x, y, z) is in this octant.
	 */
	public boolean contains(double x, double y, double z) {
		double halfLen = this.length / 2;
		return (x <= this.xmid + halfLen &&
						x >= this.xmid - halfLen &&
						y <= this.ymid + halfLen &&
						y >= this.ymid - halfLen &&
						z <= this.zmid + halfLen &&
						z >= this.zmid - halfLen);
	}

	/**
	 * Splits this octant in eight new ones.
	 */
	public Octant getOctant(int ID) {
		double x = this.xmid;
		double y = this.ymid;
		double z = this.zmid + ((ID < 4) ? -(this.length / 4) : (this.length / 4));
		double len = this.length / 2;

		switch (ID % 4) {
			case 0:
				x -= this.length / 4;
				y += this.length / 4;
				break;
			case 1:
				x += this.length / 4;
				y += this.length / 4;
				break;
			case 2:
				x += this.length / 4;
				y -= this.length / 4;
				break;
			case 3:
				x -= this.length / 4;
				y -= this.length / 4;
				break;
		}

		return new Octant(x, y, z, len);
	}

	/**
	 * Draws a Rectangle at the position and size of this octant.
	 */
	public void draw(CodeDraw cd) {
		cd.drawRectangle((((this.xmid - (this.length / 2)) / Simulation.SECTION_SIZE) * cd.getWidth()) + cd.getWidth() / 2,
						(((this.ymid - (this.length / 2)) / Simulation.SECTION_SIZE) * cd.getWidth()) + cd.getWidth() / 2,
						((this.length / Simulation.SECTION_SIZE) * cd.getWidth()),
						((this.length / Simulation.SECTION_SIZE) * cd.getHeight()));
	}
}
