import processing.core.PApplet;
import processing.core.PVector;

/**
 * A flame object that as a group make up the thruster of the players ship They
 * are red circles that shrink and more away from the ship
 */
class Flame {
	// Constant values for the flames
	final static int MAX_AGE = 50; // Max time a flame can last
	final static int MAX_SIZE = 15; // Max size a flame can be
	final static float ANGLE_VAR = 0.7f; // Half angle of the thruster
	final static float MAX_VEL = 2; // Max starting velocity of a flame
	final static float SLOW_AMOUNT = 0.97f; // Percentage flame slows to each step

	// Values for each thruster
	PVector flamePos; // Position of the flame
	PVector flameVel; // Velocity of the flame
	int flameSize = (int) random(MAX_SIZE / 2, MAX_SIZE); // Initial size of the flame
	int flameStartAge = (int) random(MAX_AGE / 2, MAX_AGE); // How long the flame will last
	int flameAge = flameStartAge; // The countdown of the flames age

	/**
	 * Creat the flame The needs the x and y positions and angle of the ship
	 */
	Flame(float xPos, float yPos, float angle) {
		angle += random(-ANGLE_VAR, ANGLE_VAR); // Modify angle by a random amount
		flamePos = new PVector(xPos, yPos); // Set the starting position
		flameVel = new PVector((float)-Math.cos(angle) * MAX_VEL, (float)-Math.sin(angle) * MAX_VEL);// Find and set velocities
	}

	/**
	 * Draw the flame Modifies the flame then draws it in its new location Flame
	 * moves, shinks and yellow shifts every time it's drawn
	 */
	void drawFlame(MainSketch canvas) {
		flameAge--; // Age the flame
		flamePos.add(flameVel); // Move the flame
		flameVel.mult(SLOW_AMOUNT); // Slow the flame based on it's speed
		canvas.fill(255, (flameStartAge - flameAge) * 255 / flameStartAge, 0);// Color flame between red and yellow based on
																		// age
		// Draw the flame
		canvas.ellipse(flamePos.x, flamePos.y, flameSize * flameAge / flameStartAge, flameSize * flameAge / flameStartAge);
	}
	
	private float random(float lower, float higher) {
		return (float) (Math.random() * (higher - lower) + lower);
	}

	// Return the age of the flame
	int getAge() {
		return flameAge;
	}
}
