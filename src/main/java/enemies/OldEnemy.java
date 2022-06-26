package enemies;

import objects.Shot;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * Old enemy class, not unused. Only for referance.
 * 
 *
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
@Deprecated
public class OldEnemy {
	// All the data about the different enemy types :

	// Type 0, Simple square that move towards player
	private static final float SIZE0 = 15, TURN_SPEED0 = 0.05f, // Max angle per frame turn rate
			VELOCITY0 = 4, // Enemies constant velocity
			ACCURACY = 0.5f; // How accurately it points at the players ship
	private static final int HEALTH0 = 1, SCORE_GIVEN0 = 1; // Score given when destroyed

	/** Type 1, Oval that moves towards player and shoots */
	private static final float SIZE1 = 30;
	/** Speed of it's shot projectiles */
	private static final float SHOT_SPEED1 = 11;
	/** Size of shot projectiles */
	private static final float SHOT_DIAM1 = 12;
	/** Maximum velocity possible when far from player */
	private static final float MAX_VEL1 = 5;
	/** Velocity when very close to the player */
	private static final float BASE_SPEED1 = -2;
	/** % of distance to player ship added as velocity */
	private static final float DIST_SPEED1 = 0.0225f;
	/** Rate of Fire */
	private static final int ROF1 = 77;
	/** ?? */
	private static final int HEALTH1 = 2;
	/** Damage of one if it shots */
	private static final int SHOT_DAMAGE1 = 3;
	private static final int SCORE_GIVEN1 = 3;

	// Type 2, unmoving mine enemy shown as six pointed star
	private static final float SIZE2 = 40, TURN_SPEED2 = 0.025f; // Angle per frame turn rate on the stop
	private static final int HEALTH2 = 5, SCORE_GIVEN2 = 4; // Score given when destroyed

	// Type 3, flat nosed triangle with raMath.PId fire
	private static final float SIZE3 = 30, SHOT_SPEED3 = 7, // Speed of this ships shots
			SHOT_DIAM3 = 17, // Size of this ships shots
			MAX_VEL3 = 9, // Max possible velocity when far from player
			BASE_SPEED3 = -6, // Velocity when very close to the player
			DIST_SPEED3 = 0.02f; // % of distance to player ship added as velocity
	private static final int HEALTH3 = 2, ROF3 = 21, // Rate of Fire
			SHOT_DAMAGE3 = 2, // Damage per shot
			SCORE_GIVEN3 = 5; // Score given when destroyed

	// Fast as little triangle enemy that kamikazes into player
	private static final float SIZE4 = 20, TURN_SPEED4 = 0.033f, // max angle per frame turn rate
			VELOCITY4 = 8, // Enemies constant velocity
			ACCURACY4 = 0.25f; // How accurately it points at the players ship
	private static final int HEALTH4 = 1, SCORE_GIVEN4 = 2;

	private static final float SPEED_DIF = 0.15f; // % added or removed from all enemies speed based off difficulty

	PVector enPos; // Position of the enemy
	float enAngle; // Angle the enemy is pointing
	float enVel = 0; // Velocities of the enemy
	int enType; // The number the represents the Type of the enemy
	int enRed = 255; // Red value of its colour
	int enGreen = (int) (Math.random() * 120); // Green value of its colour
	int enBlue = (int) (Math.random() * 120); // Blue value of the ships colour
	int enTimer = 0; // Frames since being spawn (used for timing shooting)
	float enHealth; // Enemies current health
	float enSize; // Enemies current curSize
	int enScore; // Score to be given upon being destroyed

	// Make new enemy at given x and y positions with given Type
	public OldEnemy(float x, float y, int type) {
		// Set general enemy information to stuff given
		enType = type;
		enPos = new PVector(x, y);

		// Set the enemies data based of what Type it is
		// Must include a minimum of health, curSize and score
		if (enType == 0) { // Set Type 0 data
			enSize = SIZE0;
			enHealth = HEALTH0;
			enScore = SCORE_GIVEN0;
			enVel = VELOCITY0;
			// Start it looking towards the middle of the screen on x axis
			if (x > 0) {
				enAngle = (float) Math.PI; // Make it look left
			}
		}

		else if (enType == 1) { // Set Type 1 data
			enSize = SIZE1;
			enHealth = HEALTH1;
			enScore = SCORE_GIVEN1;
		}

		else if (enType == 2) { // Set Type 2 data
			enSize = SIZE2;
			enHealth = HEALTH2;
			enScore = SCORE_GIVEN2;
		}

		else if (enType == 3) { // Set Type 3 data
			enSize = SIZE3;
			enHealth = HEALTH3;
			enScore = SCORE_GIVEN3;
		}

		else if (enType == 4) { // Set Type 4 data
			enSize = SIZE4;
			enHealth = HEALTH4;
			enScore = SCORE_GIVEN4;
			enVel = VELOCITY4;
			// Start it looking towards the middle of the screen on x axis
			if (x > 0) {
				enAngle = (float) Math.PI; // Make it look left
			}
		}
	}

	// Draw the enemy
	public void drawEnemy(PApplet canvas) {
		canvas.fill(enRed, enGreen, enBlue); // Set the colour to the correct one

		canvas.pushMatrix(); // Matrix of this enemy
		canvas.translate(enPos.x, enPos.y);
		canvas.rotate(enAngle);

		// If Type 0, draw a square
		if (enType == 0) {
			canvas.rect(-enSize / 2, -enSize / 2, enSize, enSize);
		}

		// If Type 1 draw an ellipse
		else if (enType == 1) {
			canvas.ellipse(0, 0, enSize, enSize * 0.7f);
		}

		// If Type 2 draw a six pointed star
		else if (enType == 2) {
			canvas.triangle(0, enSize / 2, (float) Math.sqrt(0.75) * enSize / 2, -enSize / 4,
					(float) (-Math.sqrt(0.75f) * enSize / 2), -enSize / 4);
			canvas.triangle(0, -enSize / 2, (float) -Math.sqrt(0.75) * enSize / 2, enSize / 4,
					(float) Math.sqrt(0.75) * enSize / 2, enSize / 4);
		}

		// If Type 3 draw a flat nosed triangle like shape
		else if (enType == 3) {
			canvas.beginShape();
			canvas.vertex(enSize / 2, enSize / 3);
			canvas.vertex(-enSize / 2, enSize / 2);
			canvas.vertex(-enSize / 4, 0);
			canvas.vertex(-enSize / 2, -enSize / 2);
			canvas.vertex(enSize / 2, -enSize / 3);
			canvas.endShape(PConstants.CLOSE);
		}

		// If Type 4 drawn a simple isosoles triangle
		else if (enType == 4) {
			canvas.triangle(enSize / 2, 0, -enSize / 4, enSize / 3, -enSize / 4, -enSize / 3);
		}

		canvas.popMatrix();
	}

	// Point enemy at and move towards given coordinates
	public void moveEnemy(PVector shipPos, int difficulty) {
		enPos.x += Math.cos(enAngle) * enVel * (1 + difficulty * SPEED_DIF);
		enPos.y += Math.sin(enAngle) * enVel * (1 + difficulty * SPEED_DIF);
		float angleToShip = (float) Math.atan((enPos.y - shipPos.y) / (enPos.x - shipPos.x));
		if (shipPos.x < enPos.x) {
			angleToShip += Math.PI;
		}

		// Type 0
		if (enType == 0) {
			if (enAngle - angleToShip > Math.PI) {
				enAngle -= 2 * Math.PI;
			} else if (angleToShip - enAngle > Math.PI) {
				enAngle += 2 * Math.PI;
			}
			if (Math.abs(angleToShip - enAngle) > ACCURACY) {
				if (angleToShip > enAngle) {
					enAngle += TURN_SPEED0;
				} else {
					enAngle -= TURN_SPEED0;
				}
			}
		}
		// Type 1
		else if (enType == 1) {
			enAngle = angleToShip;
			enVel = Math.min(MAX_VEL1, BASE_SPEED1 + DIST_SPEED1 * enPos.dist(shipPos));
		}
		// Type 2
		else if (enType == 2) {
			enAngle += TURN_SPEED2;
		}
		// Type 3
		else if (enType == 3) {
			enAngle = angleToShip;
			enVel = Math.min(MAX_VEL3, BASE_SPEED3 + DIST_SPEED3 * enPos.dist(shipPos));
		}
		// Type 4
		else if (enType == 4) {
			if (enAngle - angleToShip > Math.PI) {
				enAngle -= 2 * Math.PI;
			} else if (angleToShip - enAngle > Math.PI) {
				enAngle += 2 * Math.PI;
			}
			if (Math.abs(angleToShip - enAngle) > ACCURACY4) {
				if (angleToShip > enAngle) {
					enAngle += TURN_SPEED4;
				} else {
					enAngle -= TURN_SPEED4;
				}
			}
		}
	}

	// Make a new shot at the enemy's position
	public Shot shoot() {
		enTimer++; // Increment the enemies own internal timer
		// Type 1 is able to shoot
		if (enType == 1 && enTimer > ROF1) {
			enTimer = 0;
			return new Shot(enPos.x, enPos.y, enAngle, SHOT_SPEED1, SHOT_DIAM1, true, SHOT_DAMAGE1);
		}

		// Type 3 is able to shoot
		if (enType == 3 && enTimer > ROF3) {
			enTimer = 0;
			return new Shot(enPos.x, enPos.y, enAngle, SHOT_SPEED3, SHOT_DIAM3, true, SHOT_DAMAGE3);
		}
		return null;
	}

	// Check if the enemy it touching a ship of given location and curSize
	public boolean touching(float shipX, float shipY, int diam) {
		return Math.hypot(shipX - enPos.x, shipY - enPos.y) < (enSize + diam) / 2;
	}

	// Damaged the enemy by the given amount and returns whether it died or not
	public boolean damage(float damage) {
		enHealth -= damage;
		// Check if the damage killed the enemy
		if (enHealth <= 0) {
			return true;
		}
		// Change that enemies colour slightly to help show the shot hit
		enRed -= 40 * damage / enHealth;
		enGreen -= 10 * damage / enHealth;
		enBlue -= 10 * damage / enHealth;
		return false;
	}

	// Return the x position of the enemy
	public float getPosX() {
		return enPos.x;
	}

	// Return the y position of the enemy
	public float getPosY() {
		return enPos.y;
	}

	// Return the score given by this enemy
	public int getScore() {
		return enScore;
	}

	// Returns the enemies Type
	public int getType() {
		return enType;
	}
}
