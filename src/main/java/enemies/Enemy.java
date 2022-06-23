package enemies;

import main.MainSketch;
import objects.Shot;
import processing.core.PVector;

/**
 * The object for all enemies in the game enemies are just any hostile ship that
 * tries to harm the player. They come is various shape, sizes and behaviours.
 * 
 * @author Ethan Maxwell
 */
public abstract class Enemy {
	/** % added or removed from all enemies speed based off difficulty */
	protected static final float SPEED_DIF = 0.15f;

	/** Position of the enemy */
	protected PVector position;
	/** Angle the enemy is pointing */
	protected float angle = 0;
	/** Velocities of the enemy */
	protected float speed = 0;
	/** Red value of its colour */
	protected int redColour = 255;
	/** Green value of its colour */
	protected int greenColour = (int) (Math.random() * 120);
	/** Blue value of the ships colour */
	protected int blueColour = (int) (Math.random() * 120);
	/** Frames since last shooting (used for timing shooting) */
	protected int shootTimer = 0;
	/** Enemies current health */
	protected float curHealth;

	/**
	 * Make new enemy at given x and y positions
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	protected Enemy(float x, float y) {
		position = new PVector(x, y);
		curHealth = getMaxHealth();

		// If on the right side of the screen, look left
		if (x > 0) {
			angle = (float) Math.PI; // Make it look left
		}
	}

	/**
	 * @return The size of the enemy
	 */
	abstract protected float getSize();

	/**
	 * @return The max health of the enemy
	 */
	abstract protected int getMaxHealth();

	/**
	 * @return The score given by of the enemy
	 */
	abstract public int getScore();

	/**
	 * Draw the enemy.
	 * 
	 * @param canvas The canvas to draw the enemy on
	 */
	public void drawEnemy(MainSketch canvas) {
		canvas.fill(redColour, greenColour, blueColour); // Set the colour to the correct one

		canvas.pushMatrix(); // Matrix of this enemy
		canvas.translate(position.x, position.y);
		canvas.rotate(angle);

		drawEnemyShape(canvas);

		canvas.popMatrix();
	}

	/**
	 * Draw the shape for this enemy.
	 * 
	 * @param canvas The canvas to draw the shape on
	 */
	protected abstract void drawEnemyShape(MainSketch canvas);

	/**
	 * Point enemy at and move towards given the players ship.
	 * 
	 * @param shipPos    Position of player ship
	 * @param difficulty The current game difficulty
	 */
	public void moveEnemy(PVector shipPos, int difficulty) {
		shootTimer++;

		position.x += Math.cos(angle) * speed * (1 + difficulty * SPEED_DIF);
		position.y += Math.sin(angle) * speed * (1 + difficulty * SPEED_DIF);

		modVelocity(shipPos);
	}

	/**
	 * Modify the angle and velocity of the ship
	 * 
	 * @param shipPos Position of the players ship
	 */
	protected abstract void modVelocity(PVector shipPos);

	/**
	 * Try to make a new shot for this enemy. Returns null if the enemy is not
	 * shooting this frame.
	 * 
	 * @return The new shot, or null if enemy is not shooting
	 */
	abstract public Shot shoot();

	/**
	 * Check if the enemy it touching a something of given location and size.
	 * 
	 * @param objectXLoc X location of the object to see if the enemy is touching
	 * @param objectYLoc Y location of the object to see if the enemy is touching
	 * @param objectDiam Diameter of the object to see if the enemy is touching
	 * @return If the object is touching the enemy
	 */
	public boolean touching(float objectXLoc, float objectYLoc, int objectDiam) {
		return Math.hypot(objectXLoc - position.x, objectYLoc - position.y) < (getSize() + objectDiam) / 2;
	}

	/**
	 * Damaged the enemy by the given amount and returns whether it died or not.
	 * 
	 * @param damage The amount of damage to deal to enemy
	 * @return If the enemy died from the damage
	 */
	public boolean damage(float damage) {
		curHealth -= damage;
		// Check if the damage killed the enemy
		if (curHealth <= 0) {
			return true;
		}
		// Change that enemies colour slightly to help show the shot hit
		redColour -= 40 * damage / curHealth;
		greenColour -= 10 * damage / curHealth;
		blueColour -= 10 * damage / curHealth;
		return false;
	}

	/**
	 * @return The x position of the enemy
	 */
	public float getPosX() {
		return position.x;
	}

	/**
	 * @return The y position of the enemy
	 */
	public float getPosY() {
		return position.y;
	}

}
