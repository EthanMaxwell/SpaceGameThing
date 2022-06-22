package objects;
import main.MainSketch;
import processing.core.PVector;

/**
 * Crates that spawn over time all across them map They can be picked up to
 * apply it's buff number to the player
 * 
 * @author Ethan Maxwell
 */
public class Crate {
	/** Size of all the crates */
	private final static int CRATE_SIZE = 30;
	/** x and y position of the crate */
	private PVector cratePos;
	// TODO: Make into a power up object
	/** The number value the shows what buff the crate will give */
	private int crateType;

	/**
	 * Create a crate at the given coordinates
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Crate(float xPos, float yPos) {
		cratePos = new PVector(xPos, yPos);
		// TODO: make into new PowerUp();
		crateType = (int) (Math.random() * 5); // Set it to a random buff number
	}

	/**
	 * Check if ship a given coordinate and size is touching the crate
	 * 
	 * @param shipX    X position of the ship
	 * @param shipY    Y Position of the ship
	 * @param shipDiam The diameter of the ship
	 * @return If the ship touching the object
	 */
	public boolean touching(float shipX, float shipY, float shipDiam) {
		return Math.hypot(shipX - cratePos.x, shipY - cratePos.y) < (shipDiam + CRATE_SIZE) / 2;
	}

	/**
	 * Draw the crate
	 * 
	 * @param canvas The canvas to draw the crate onto
	 */
	public void drawCrate(MainSketch canvas) {
		canvas.fill(10, 255, 50);
		canvas.ellipse(cratePos.x, cratePos.y, CRATE_SIZE, CRATE_SIZE); // Draw a circle
	}

	/** Return this crate buff
	 * 
	 * @return The power up type
	 */
	public int getPowerUp() {
		return crateType;
	}
}
