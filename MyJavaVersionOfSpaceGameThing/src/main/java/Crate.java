import processing.core.PApplet;
import processing.core.PVector;

/**
 * Crates that spawn over time all across them map They can be picked up to
 * apply it's buff number to the player
 */
class Crate {
	final static int CRATE_SIZE = 30; // Size of all the crates

	PVector cratePos; // x and y position of the crate
	// TODO: Make into a power up object
	int crateType; // The number value the shows what buff the crate will give

	// Create a crate at the given coordinates
	public Crate(float xPos, float yPos) {
		cratePos = new PVector(xPos, yPos);
		// TODO: make into new PowerUp();
		crateType = (int) (Math.random() * 5); // Set it to a random buff number
	}

	// Check if ship a given coordinate and size is touching the crate
	boolean touching(float shipX, float shipY, float shipDiam) {
		return Math.hypot(shipX - cratePos.x, shipY - cratePos.y) < (shipDiam + CRATE_SIZE) / 2;
	}

	// Draw the crate
	void drawCrate(MainSketch canvas) {
		canvas.fill(10, 255, 50);
		canvas.ellipse(cratePos.x, cratePos.y, CRATE_SIZE, CRATE_SIZE); // Draw a circle
	}

	// Return this crate buff
	int getPowerUp() {
		return crateType;
	}
}
