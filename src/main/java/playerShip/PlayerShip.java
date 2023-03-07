package playerShip;

import java.util.List;

import objects.Flame;
import objects.Shot;
import powerups.PowerUp;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class PlayerShip {

	/** The inaccuracy of the players shots */
	private static final float SHOT_INACU = 0.25f;
	/** Size of the players shots */
	private static final float SHOT_SIZE = 10f;
	/** Velocity of the players shots */
	private static final float SHOT_VEL = 11f;
	/** Base damage of players shots (modified by power ups) */
	private static final float SHOT_DAMAGE = 1f;
	/** Max acceleration of the ship */
	private static final float SHIP_ACC = 0.2f;
	/** Max rotational acceleration of the ship */
	private static final float SHIP_ROTACC = 0.006f;
	/** Percentage ship slows to each step */
	private static final float DECEL = 0.96f;

	/** Player normal starting health (modified by difficulty) */
	private static final int BASE_HEALTH = 35;
	/** Difference is health based off difficulty */
	private static final int HEALTH_DIF = 15;
	/** Frames between each shot the player can make */
	private static final int SHOT_TIME = 15;
	/** Radius of the players ships hit box */
	private static final int SHIP_RAD = 20;

	/** Colour of the players ship */
	private final int SHIP_COLOUR = 100;

	private PVector shipPos, // Position of the players ship
			shipVel; // Velocities of the players ship

	private float curShipAcc, // The ships current acceleration
			shipAngle, // The ships current angle
			shipRotVel, // The ships current rotational velocity
			curShipRotAcc, // The ships current rotational acceleration
			curShotInacu, // The inaccuracy of the players shots
			curShotSize, // Size of the players shots
			curShotVel, // Velocity of the players shots
			curShotDamage, // Current damage the player does with each shot
			curShotTime, // current frames between each shot the player can make
			shipHealth; // Player current health

	private int shotTimer = SHOT_TIME; // Players current cool down on shooting

	/**
	 * Create new player ship with default parameters for the given difficulty
	 * 
	 * @param difficulty
	 */
	public PlayerShip(int difficulty) {
		shipPos = new PVector(0, 0);
		shipVel = new PVector(0, 0);
		curShotDamage = SHOT_DAMAGE;
		curShotInacu = SHOT_INACU;
		curShotSize = SHOT_SIZE;
		curShotVel = SHOT_VEL;
		curShotTime = SHOT_TIME;
		shipHealth = BASE_HEALTH - HEALTH_DIF * difficulty;
	}
	
	/**
	 * Make the ship shoot if they can. Shot direction is based of given mouse and screen position.
	 * 
	 * @param mouseX X position of the mouse
	 * @param mouseY Y position of the mouse
	 * @param screenPos Current position of the screen
	 * @return The shot fired, null of shot can't be fired.
	 */
	public Shot shoot(int mouseX, int mouseY, PVector screenPos) {
		shotTimer++;
		if (shotTimer >= curShotTime) {
			shotTimer = 0; // Reset the timer since they last shot
			return new Shot(shipPos.x, shipPos.y,
					(float) (Math.atan2(mouseY - screenPos.y - shipPos.y, mouseX - screenPos.x - shipPos.x)
							- curShotInacu + Math.random() * 2 * curShotInacu),
					curShotVel, curShotSize, false, curShotDamage);
		}

		return null;
	}

	/**
	 * Does all operations to do with the velocity of the players ship It calls the
	 * draw function to draw the ship afterwards.
	 * 
	 * @param worldRad Radius of the world (to stop play leaving world)
	 * 
	 */
	public void modShip(int worldRad) {
		// Check if ship has collided with any of the sides and stop it if it has
		if (shipPos.x > worldRad - SHIP_RAD) { // Left wall
			shipPos.x = worldRad - SHIP_RAD;
		} else if (shipPos.x < -worldRad + SHIP_RAD) { // Right wall
			shipPos.x = -worldRad + SHIP_RAD;
		}
		if (shipPos.y > worldRad - SHIP_RAD) { // Bottom wall
			shipPos.y = worldRad - SHIP_RAD;
		} else if (shipPos.y < -worldRad + SHIP_RAD) { // Top wall
			shipPos.y = -worldRad + SHIP_RAD;
		}

		shipRotVel += curShipRotAcc; // Increase ships rotational velocity if required
		shipRotVel = shipRotVel * DECEL; // Decelerate the ships rotational velocity
		shipAngle += shipRotVel; // Rotate the ship
		shipVel.x += Math.cos(shipAngle) * curShipAcc; // Set y velocity based off angle
		shipVel.y += Math.sin(shipAngle) * curShipAcc; // Set x velocity based of angle
		shipVel.mult(DECEL); // Decelerate the ship
		shipPos.add(shipVel); // Move the ship
	}

	/**
	 * Damage the ship by the given value.
	 * @param damage Amount of damage to do to the ship
	 */
	public void damage(float damage) {
		shipHealth -= damage;
	}

	/**public void applyPowerUps(List<PowerUp> powerUps) {

		// Reset stats
		curShotDamage = SHOT_DAMAGE;
		curShotInacu = SHOT_INACU;
		curShotSize = SHOT_SIZE;
		curShotVel = SHOT_VEL;
		curShotTime = SHOT_TIME;

		for (PowerUp powerUp : powerUps) {

			switch (powerUp.getType()) {

			// Heals player ship per frame
			case HealingKit:
				shipHealth += 0.022;
				break;
			// Increase shot accuracy, velocity and damage
			case SniperShot:
				curShotInacu /= 10;
				curShotVel *= 1.65;
				curShotDamage *= 2;
				break;
			// Greatly increases the players ships rate of fire
			case RapidFire:
				curShotTime /= 1.8;
				break;
			// Increase shot size and damage
			case HeavyShot:
				curShotSize *= 6;
				curShotDamage *= 5;
				break;
			// Increase rate of fire a lot at cost of shot accuracy, size and damage
			case URF:
				curShotTime /= 8;
				curShotInacu *= 2.1;
				curShotSize /= 1.3;
				curShotVel /= 1.4;
				curShotDamage /= 3;
				break;
			default:
				break;
			}
		}

	}*/

	/**
	 * Draws the players ship with the little cannon on the top.
	 * 
	 * @param screenPos Current position of the screen
	 * @param canvas    Canvas to draw the ship too
	 */
	public void drawShip(PVector screenPos, PApplet canvas) {
		// Matrix of the ships position and curSize
		canvas.pushMatrix();
		canvas.translate(shipPos.x, shipPos.y);
		canvas.scale(16 / (float) SHIP_RAD);

		// Matrix specifically for the ship's body angle
		canvas.pushMatrix();
		canvas.rotate(shipAngle);

		canvas.fill(SHIP_COLOUR);
		// Shape of the body of the ship
		canvas.beginShape();
		canvas.vertex(-5, 0);
		canvas.vertex(-15, -15);
		canvas.vertex(25, 0);
		canvas.vertex(-15, 15);
		canvas.endShape(PConstants.CLOSE);

		canvas.popMatrix();

		// Matrix specifically for the ships cannon angle
		canvas.pushMatrix();
		canvas.rotate(
				(float) Math.atan2(canvas.mouseY - screenPos.y - shipPos.y, canvas.mouseX - screenPos.x - shipPos.x));

		canvas.fill(200, 200, 255);
		canvas.triangle(-5, 8, -5, -8, 15, 0);// The shape of the cannon

		canvas.popMatrix();
		canvas.popMatrix();
	}

	/**
	 * Called every frame where new thruster flame should be drawn. return null if
	 * no thruster is to be drawn.
	 * 
	 * @return The new thruster flame particle
	 */
	public Flame drawThruster() {
		if (curShipAcc > 0) {
			return new Flame(shipPos.x, shipPos.y, shipAngle);
		}
		return null;
	}

	/**
	 * Place ship into a state of accelerating.
	 */
	public void accelerate() {
		if (curShipAcc == 0)
			curShipAcc = SHIP_ACC;
	}
	
	/**
	 * Place ship into a state of turning left.
	 */
	public void turnLeft() {
		if (curShipRotAcc == 0)
			curShipRotAcc -= SHIP_ROTACC;
	}

	/**
	 * Place ship into a state of turning right.
	 */
	public void turnRight() {
		if (curShipRotAcc == 0)
			curShipRotAcc = SHIP_ROTACC;
	}

	/**
	 * Place the ship into a state of not accelerating.
	 */
	public void stop() {
		if (curShipAcc > 0)
			curShipAcc = 0;
	}
	
	/**
	 * Place the ship into a state on not turning.
	 */
	public void stopTurning() {
		curShipRotAcc = 0;
	}
	
	/**
	 * @return If the ship is currently dead
	 */
	public boolean isDead() {
		return shipHealth <= 0;
	}

	/**
	 * @return x position of ship
	 */
	public float getXPos() {
		return shipPos.x;
	}

	/**
	 * @return y position of ship
	 */
	public float getYPos() {
		return shipPos.y;
	}
	
	/**
	 * @return The ship current health
	 */
	public float getHealth() {
		return shipHealth;
	}
	
	/**
	 * @return Int value of the ships colour
	 */
	public int getColour() {
		return SHIP_COLOUR;
	}
	
	/**
	 * @return x velocity of ship
	 */
	public float getXVel() {
		return shipVel.x;
	}

	/**
	 * @return y velocity of ship
	 */
	public float getYVel() {
		return shipVel.y;
	}

	/**
	 * @return Collision radius of the ship
	 */
	public float getShipRad() {
		return SHIP_RAD;
	}

}
