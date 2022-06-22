import processing.core.PVector;

/**
 * A flame object that as a group make up the thruster of the players ship They
 * are red circles that shrink and more away from the ship.
 * 
 * @author Ethan Maxwell
 */
public class Flame {
	// Constant values for the flames
	/** Max time a flame can last */
	private final static int MAX_AGE = 50;
	/** Max size a flame can be */
	private final static int MAX_SIZE = 15;
	/** Half angle of the thruster */
	private final static float ANGLE_VAR = 0.7f;
	/** Max starting velocity of a flame */
	private final static float MAX_VEL = 2;
	/** Percentage flame slows to each step */
	private final static float SLOW_AMOUNT = 0.97f;

	// Values for each thruster
	/** Position of the flame */
	private PVector flamePos;
	/** Velocity of the flame */
	private PVector flameVel;
	/** Initial size of the flame */
	private int flameSize = (int) random(MAX_SIZE / 2, MAX_SIZE);
	/** How long the flame will last */
	private int flameStartAge = (int) random(MAX_AGE / 2, MAX_AGE);
	/** The count down of the flames age */
	private int flameAge = flameStartAge;

	/**
	 * Create the flame The needs the x and y positions and angle of the ship.
	 * 
	 * @param xPos  Initial x position of the flame
	 * @param yPos  Initial y position of the flame
	 * @param angle The angle that the flame should go in
	 */
	public Flame(float xPos, float yPos, float angle) {
		angle += random(-ANGLE_VAR, ANGLE_VAR); // Modify angle by a random amount
		flamePos = new PVector(xPos, yPos); // Set the starting position
		// Find and set velocities
		flameVel = new PVector((float) -Math.cos(angle) * MAX_VEL, (float) -Math.sin(angle) * MAX_VEL);
	}

	/**
	 * Draw the flame Modifies the flame then draws it in its new location Flame
	 * moves, shrinks and yellow shifts every time it's drawn.
	 * 
	 * @param canvas Canvas to draw flame too.
	 */
	public void drawFlame(MainSketch canvas) {
		flameAge--; // Age the flame
		flamePos.add(flameVel); // Move the flame
		flameVel.mult(SLOW_AMOUNT); // Slow the flame based on it's speed
		// Colour flame between red and yellow based on age
		canvas.fill(255, (flameStartAge - flameAge) * 255 / flameStartAge, 0);
		// Draw the flame
		canvas.ellipse(flamePos.x, flamePos.y, flameSize * flameAge / flameStartAge,
				flameSize * flameAge / flameStartAge);
	}

	/**
	 * Create a random float between the two numbers.
	 * 
	 * @param lower  The lower bound of the range
	 * @param higher The upper bound of the range
	 * @return The random number generated
	 */
	private float random(float lower, float higher) {
		return (float) (Math.random() * (higher - lower) + lower);
	}

	/**
	 * Get the age of the flame
	 * 
	 * @return The flames age
	 */
	public int getAge() {
		return flameAge;
	}
}
