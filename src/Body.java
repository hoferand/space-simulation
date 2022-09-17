import codedraw.CodeDraw;

import java.util.Objects;

/**
 * Represents a celestial body.
 */
public class Body {
	/** The mass of this body. */
	private double mass;

	/** The position of this the mass center. */
	private Vector3 massCenter;

	/** The current direction of the movement. */
	private Vector3 currentMovement;


	public Body(double mass, Vector3 massCenter, Vector3 currentMovement) {
		this.mass = mass;
		this.massCenter = massCenter;
		this.currentMovement = currentMovement;
	}

	/**
	 * Returns the distance between the given body and this body.
	 */
	public double distanceTo(Body b) {
		return this.massCenter.distanceTo(b.massCenter);
	}

	/**
	 * Returns a vector representing the gravitational force exerted by the given body on this body.
	 */
	public Vector3 gravitationalForce(Body b) {
		Vector3 direction = b.massCenter.minus(this.massCenter);
		double distance = direction.length();
		direction.normalize();
		double force = Simulation.G * this.mass * b.mass / (distance * distance) * 100;

		return direction.times(force);
	}

	/**
	 * Moves this body to a new position, according to the given force and updates the current movement.
	 */
	public void move(Vector3 force) {
		Vector3 newPosition = this.massCenter.plus(force.times(1 / this.mass)).plus(this.currentMovement);
		Vector3 newMovement = newPosition.minus(this.massCenter);
		this.massCenter = newPosition;
		this.currentMovement = newMovement;
	}

	/**
	 * Returns a new body that is formed by the collision of the given body and this body.
	 */
	public Body merge(Body b) {
		double mass = this.mass + b.mass;
		Vector3 massCenter = this.massCenter.times(this.mass).plus(b.massCenter.times(b.mass)).times(1 / mass);
		Vector3 currentMovement = this.currentMovement.times(this.mass).plus(b.currentMovement.times(b.mass)).times(1 / mass);
		return new Body(mass, massCenter, currentMovement);
	}

	/**
	 * Draws the body to the given canvas as a filled circle.
	 */
	public void draw(CodeDraw cd) {
		cd.setColor(SpaceDraw.massToColor(this.mass));
		this.massCenter.drawAsFilledCircle(cd, SpaceDraw.massToRadius(this.mass));
	}

	/**
	 * Returns the mass of this body.
	 */
	public double mass() {
		return this.mass;
	}

	/**
	 * Checks if this body is within the given octant.
	 */
	public boolean in(Octant o) {
		return massCenter.in(o);
	}

	/**
	 * Returns a readable representation of this body.
	 */
	@Override
	public String toString() {
		return this.mass + " kg, position: " + this.massCenter.toString() + " m, movement: " + this.currentMovement.toString() + " m/s.";
	}

	/**
	 * Compares the given object with this body.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Body body = (Body) o;
		return Double.compare(body.mass, mass) == 0 && Objects.equals(massCenter, body.massCenter) && Objects.equals(currentMovement, body.currentMovement);
	}
}

