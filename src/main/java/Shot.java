import processing.core.PVector;

/**
 * The object which is every projectile They can be friendly or hostile They
 * move across the screen and collide with ships.
 * 
 * @author Ethan Maxwell
 */
public class Shot {
	/** Position of the shot */
	private PVector shotPos;
	/** Angle for direction that it's moving */
	private float shotAngle;
	/** The velocity of the shot */
	private float shotVel;
	/** The diameter size of the shot */
	private float shotDiam;
	/** The damage of the shot */
	private float shotDamage;
	/** Weather the shot is hostile to the player ship */
	private boolean shotHostile;

	/**
	 * Creates a new shot for given x and y positions, angle, velocity, diameter and
	 * hostility.
	 * 
	 * @param x        Initial x position of the shot
	 * @param y        Initial y position of the shot
	 * @param angle    Angle of the shot
	 * @param velocity Velocity of the shot
	 * @param diameter Diameter of the shot
	 * @param hostile  If the shot is hostile of the player ship
	 * @param damage   The damage of shot will deal
	 */
	public Shot(float x, float y, float angle, float velocity, float diameter, boolean hostile, float damage) {
		// Set the properties of the shot to the specified ones
		shotPos = new PVector(x, y);
		shotAngle = angle;
		shotVel = velocity;
		shotDiam = diameter;
		shotHostile = hostile;
		shotDamage = damage;
	}

	/**
	 * Draw the shot on the screen.
	 * 
	 * @param canvas The canvas to draw the shot on
	 */
	public void drawShot(MainSketch canvas) {
		if (shotHostile) {
			canvas.fill(255, 0, 0); // Red
		} else {
			canvas.fill(100, 150, 255); // Blue
		}
		canvas.ellipse(shotPos.x, shotPos.y, shotDiam, shotDiam);
	}

	/**
	 * Move the shot based of its own velocity.
	 */
	public void moveShot() {
		shotPos.x += Math.cos(shotAngle) * shotVel;
		shotPos.y += Math.sin(shotAngle) * shotVel;
	}

	/**
	 * Check if the shot is when a specified box.
	 * 
	 * @param x   X location of the box centre
	 * @param y   Y location of the box centre
	 * @param rad Half the diameter of the box
	 * @return If the shot is within the box
	 */
	public boolean within(float x, float y, float rad) {
		return Math.abs(shotPos.x - x) < rad && Math.abs(shotPos.y - y) < rad;
	}

	/**
	 * Check if the shot is colliding with a ship of given location, size and
	 * hostility.
	 * 
	 * @param shipX    Ships x location
	 * @param shipY    Ships y location
	 * @param shipDiam Diameter of the ship
	 * @param yourShip If it's the players ship (or enemy)
	 * @return If they shot has collided
	 */
	public boolean touching(float shipX, float shipY, float shipDiam, boolean yourShip) {
		return (yourShip == shotHostile)
				&& Math.hypot(shipX - shotPos.x, shipY - shotPos.y) < (shipDiam + shotDiam) / 2;
	}

	/**
	 * Check if the shot is currently on screen.
	 * 
	 * @param screenPos The current position of the player screen
	 * @param width     The width of the screen
	 * @param height    The height of the screen
	 * @return If the shot is on screen
	 */
	public boolean notOnScreen(PVector screenPos, float width, float height) {
		return shotPos.x > -screenPos.x + width || shotPos.x < -screenPos.x || shotPos.y > -screenPos.y + height
				|| shotPos.y < -screenPos.y;
	}

	/**
	 * Get the damage with shot will deal.
	 * 
	 * @return The damage of the shot
	 */
	public float getDamage() {
		return shotDamage;
	}
}
