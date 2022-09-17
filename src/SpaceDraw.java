import java.awt.*;

/**
 * Collection of helper functions.
 */
public class SpaceDraw {
	/**
	 * Returns the approximate radius of a celestial body with the specified mass.
	 */
	public static double massToRadius(double mass) {
		return Simulation.SUN_RADIUS * (Math.pow(mass / Simulation.SUN_MASS, 0.5));
	}

	/**
	 * Returns the approximate color of a celestial body with the specified mass.
	 */
	public static Color massToColor(double mass) {
		Color color;
		if (mass < Simulation.SUN_MASS / 10) {
			color = Color.LIGHT_GRAY;
		} else {
			color = SpaceDraw.kelvinToColor((int) (5500 * mass / Simulation.SUN_MASS));
		}

		return color;
	}

	/**
	 * Returns the approximate color of temperature in kelvin.
	 */
	private static Color kelvinToColor(int kelvin) {
		double k = kelvin / 100D;
		double red = k <= 66 ? 255 : 329.698727446 * Math.pow(k - 60, -0.1332047592);
		double green = k <= 66 ? 99.4708025861 * Math.log(k) - 161.1195681661 : 288.1221695283 * Math.pow(k - 60, -0.0755148492);
		double blue = k >= 66 ? 255 : (k <= 19 ? 0 : 138.5177312231 * Math.log(k - 10) - 305.0447927307);

		return new Color(
						limitAndDarken(red, kelvin),
						limitAndDarken(green, kelvin),
						limitAndDarken(blue, kelvin)
		);
	}

	/**
	 * A transformation used in the method 'kelvinToColor'.
	 */
	private static int limitAndDarken(double color, int kelvin) {
		int kelvinNorm = kelvin - 373;

		if (color < 0 || kelvinNorm < 0) return 0;
		else if (color > 255) return 255;
		else if (kelvinNorm < 500) return (int) ((color / 256D) * (kelvinNorm / 500D) * 256);
		else return (int) color;
	}
}
