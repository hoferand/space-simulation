import codedraw.CodeDraw;

/**
 * Represents a vector in a 3D vector space.
 */
public class Vector3 {
	/** The x value of this vector. */
	private double x;

	/** The y value of this vector. */
	private double y;

	/** The z value of this vector. */
	private double z;


	public Vector3() {

	}

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the sum of this vector and the given vector.
	 */
	public Vector3 plus(Vector3 v) {
		return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
	}

	/**
	 * Returns the difference of this vector and the given vector.
	 */
	public Vector3 minus(Vector3 v) {
		return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
	}

	/**
	 * Adds the given Vector to this vector.
	 */
	public void add(Vector3 v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
	}

	/**
	 * Returns the product of this vector and the given factor.
	 */
	public Vector3 times(double d) {
		return new Vector3(this.x * d, this.y * d, this.z * d);
	}

	/**
	 * Returns the euclidean distance of this vector to the given vector.
	 */
	public double distanceTo(Vector3 v) {
		v = this.minus(v);

		return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}

	/**
	 * Returns the length of this vector.
	 */
	public double length() {
		return this.distanceTo(new Vector3()); // distance to origin.
	}

	/**
	 * Normalizes this vector.
	 */
	public void normalize() {
		double length = this.length();
		if (length == 0) {
			return;
		}
		this.x /= length;
		this.y /= length;
		this.z /= length;
	}

	/**
	 * Draws a filled circle with a specified radius centered at the (x, y) coordinates of this vector.
	 */
	public void drawAsFilledCircle(CodeDraw cd, double radius) {
		double x = cd.getWidth() * (this.x + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
		double y = cd.getWidth() * (this.y + Simulation.SECTION_SIZE / 2) / Simulation.SECTION_SIZE;
		radius = cd.getWidth() * radius / Simulation.SECTION_SIZE;
		cd.fillCircle(x, y, Math.max(radius, 1.5));
	}

	/**
	 * Checks if this Vector is in the given Octant.
	 */
	public boolean in(Octant o) {
		return o.contains(this.x, this.y, this.z);
	}

	/**
	 * Returns a readable representation of this vector.
	 */
	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + "," + this.z + "]";
	}
}

