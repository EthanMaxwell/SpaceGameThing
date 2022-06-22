package enemies;

import main.MainSketch;
import objects.Shot;
import processing.core.PVector;

/**
 * Types of enemies: 0. square that moves kamikazes towards player 1. oval that
 * positions itself slightly away from the player and shoots 2. Unmoving yet
 * durable star shaped mine enemy 3. Like 1, but with more health, rate of fire
 * and stays further form the player 4. A small yet wicked fast kamikaze
 * triangle
 * 
 * /**The object for all enemies in the game enemies are just any hostile ship
 * that tries to harm the player They come is various shape, sizes and
 * behaviours
 */
public abstract class Enemy {
	protected static final float SPEED_DIF = 0.15f; // % added or removed from all enemies speed based off difficulty

	protected PVector position; // Position of the enemy
	protected float angle; // Angle the enemy is pointing
	protected float speed = 0; // Velocities of the enemy
	protected int redColour = 255; // Red value of its colour
	protected int greenColour = (int) (Math.random() * 120); // Green value of its colour
	protected int blueColour = (int) (Math.random() * 120); // Blue value of the ships colour
	protected int age = 0; // Frames since being spawn (used for timing shooting)
	protected float curHealth; // Enemies current health

	// Make new enemy at given x and y positions with given type
	public Enemy(float x, float y, int type) {
		position = new PVector(x, y);

		// Set the enemies data based of what type it is
		// Must include a minimum of health, curSize and score
		if (enType == 0) { // Set type 0 data
			enSize = SIZE0;
			curHealth = HEALTH0;
			enScore = SCORE_GIVEN0;
			speed = VELOCITY0;
			// Start it looking towards the middle of the screen on x axis
			if (x > 0) {
				angle = (float) Math.PI; // Make it look left
			}
		}

		else if (enType == 1) { // Set type 1 data
			enSize = SIZE1;
			curHealth = HEALTH1;
			enScore = SCORE_GIVEN1;
		}

		else if (enType == 2) { // Set type 2 data
			enSize = SIZE2;
			curHealth = HEALTH2;
			enScore = SCORE_GIVEN2;
		}

		else if (enType == 3) { // Set type 3 data
			enSize = SIZE3;
			curHealth = HEALTH3;
			enScore = SCORE_GIVEN3;
		}

		else if (enType == 4) { // Set type 4 data
			enSize = SIZE4;
			curHealth = HEALTH4;
			enScore = SCORE_GIVEN4;
			speed = VELOCITY4;
			// Start it looking towards the middle of the screen on x axis
			if (x > 0) {
				angle = (float) Math.PI; // Make it look left
			}
		}
	}

	/**
	 * @return The size of the enemy
	 */
	abstract protected float getSize();

	/**
	 * @return The max health of the enemy
	 */
	abstract protected float getMaxHealth();

	/**
	 * @return The score given by of the enemy
	 */
	abstract protected public getScore();

	// Draw the enemy
	public void drawEnemy(MainSketch canvas) {
		canvas.fill(redColour, greenColour, blueColour); // Set the colour to the correct one

		canvas.pushMatrix(); // Matrix of this enemy
		canvas.translate(position.x, position.y);
		canvas.rotate(angle);

		// If type 0, draw a square
		if (enType == 0) {
			canvas.rect(-enSize / 2, -enSize / 2, enSize, enSize);
		}

		// If type 1 draw an ellipse
		else if (enType == 1) {
			canvas.ellipse(0, 0, enSize, enSize * 0.7f);
		}

		// If type 2 draw a six pointed star
		else if (enType == 2) {
			canvas.triangle(0, enSize / 2, (float) Math.sqrt(0.75) * enSize / 2, -enSize / 4,
					(float) (-Math.sqrt(0.75f) * enSize / 2), -enSize / 4);
			canvas.triangle(0, -enSize / 2, (float) -Math.sqrt(0.75) * enSize / 2, enSize / 4,
					(float) Math.sqrt(0.75) * enSize / 2, enSize / 4);
		}

		// If type 3 draw a flat nosed triangle like shape
		else if (enType == 3) {
			canvas.beginShape();
			canvas.vertex(enSize / 2, enSize / 3);
			canvas.vertex(-enSize / 2, enSize / 2);
			canvas.vertex(-enSize / 4, 0);
			canvas.vertex(-enSize / 2, -enSize / 2);
			canvas.vertex(enSize / 2, -enSize / 3);
			canvas.endShape(canvas.CLOSE);
		}

		// If type 4 drawn a simple isosoles triangle
		else if (enType == 4) {
			canvas.triangle(enSize / 2, 0, -enSize / 4, enSize / 3, -enSize / 4, -enSize / 3);
		}

		canvas.popMatrix();
	}

	// Point enemy at and move towards given coordinates
	public void moveEnemy(PVector shipPos, int difficulty) {
		position.x += Math.cos(angle) * speed * (1 + difficulty * SPEED_DIF);
		position.y += Math.sin(angle) * speed * (1 + difficulty * SPEED_DIF);
		float angleToShip = (float) Math.atan((position.y - shipPos.y) / (position.x - shipPos.x));
		if (shipPos.x < position.x) {
			angleToShip += Math.PI;
		}
		// Type 0
		if (enType == 0) {
			if (angle - angleToShip > Math.PI) {
				angle -= 2 * Math.PI;
			} else if (angleToShip - angle > Math.PI) {
				angle += 2 * Math.PI;
			}
			if (Math.abs(angleToShip - angle) > ACCURACY) {
				if (angleToShip > angle) {
					angle += TURN_SPEED0;
				} else {
					angle -= TURN_SPEED0;
				}
			}
		}
		// Type 1
		else if (enType == 1) {
			angle = angleToShip;
			speed = Math.min(MAX_VEL1, BASE_SPEED1 + DIST_SPEED1 * position.dist(shipPos));
		}
		// Type 2
		else if (enType == 2) {
			angle += TURN_SPEED2;
		}
		// Type 3
		else if (enType == 3) {
			angle = angleToShip;
			speed = Math.min(MAX_VEL3, BASE_SPEED3 + DIST_SPEED3 * position.dist(shipPos));
		}
		// Type 4
		else if (enType == 4) {
			if (angle - angleToShip > Math.PI) {
				angle -= 2 * Math.PI;
			} else if (angleToShip - angle > Math.PI) {
				angle += 2 * Math.PI;
			}
			if (Math.abs(angleToShip - angle) > ACCURACY4) {
				if (angleToShip > angle) {
					angle += TURN_SPEED4;
				} else {
					angle -= TURN_SPEED4;
				}
			}
		}
	}

	// Make a new shot at the enemy's position
	public Shot shoot() {
		age++; // Increment the enemies own internal timer
		// Type 1 is able to shoot
		if (enType == 1 && age > ROF1) {
			age = 0;
			return new Shot(position.x, position.y, angle, SHOT_SPEED1, SHOT_DIAM1, true, SHOT_DAMAGE1);
		}

		// Type 3 is able to shoot
		if (enType == 3 && age > ROF3) {
			age = 0;
			return new Shot(position.x, position.y, angle, SHOT_SPEED3, SHOT_DIAM3, true, SHOT_DAMAGE3);
		}
		return null;
	}

	// Check if the enemy it touching a ship of given location and curSize
	public boolean touching(float shipX, float shipY, int diam) {
		return Math.hypot(shipX - position.x, shipY - position.y) < (enSize + diam) / 2;
	}

	// Damaged the enemy by the given amount and returns whether it died or not
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

	// Return the x position of the enemy
	public float getPosX() {
		return position.x;
	}

	// Return the y position of the enemy
	public float getPosY() {
		return position.y;
	}
}
