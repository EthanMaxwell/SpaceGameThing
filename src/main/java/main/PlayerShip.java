package main;

import objects.Flame;
import objects.Shot;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class PlayerShip {
	private static final float
	/** The inaccuracy of the players shots */
	SHOT_INACU = 0.25f, SHOT_SIZE = 10f, // Size of the players shots
			SHOT_VEL = 11f, // Velocity of the players shots
			SHOT_DAMAGE = 1f, // Base damage of players shots (modified by power ups)
			SHIP_ACC = 0.2f, // Max acceleration of the ship
			SHIP_ROTACC = 0.006f, // Max rotational acceleration of the ship
			DECEL = 0.96f; // Percentage ship slows to each step

	private static final int BASE_HEALTH = 35, // Player normal starting health (modified by difficulty)
			HEALTH_DIF = 15, // Difference is health based off difficulty
			SHOT_TIME = 15, // Frames between each shot the player can make
			SHIP_RAD = 20; // Radius of the players ships hitbox

	private final int SHIP_COLOUR = 100; // Colour of the players ship

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

	private int shotTimer = SHOT_TIME; // Players current cooldown on shooting

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

	public void damage(float damage) {
		shipHealth -= damage;
	}

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

	public Flame drawThruster() {
		if (curShipAcc > 0) {
			return new Flame(shipPos.x, shipPos.y, shipAngle);
		}
		return null;
	}

	public void accelerate() {
		if (curShipAcc == 0)
			curShipAcc = SHIP_ACC;
	}

	public void turnLeft() {
		if (curShipRotAcc == 0)
			curShipRotAcc -= SHIP_ROTACC;
	}

	public void turnRight() {
		if (curShipRotAcc == 0)
			curShipRotAcc = SHIP_ROTACC;
	}

	public void stop() {
		if (curShipAcc > 0)
			curShipAcc = 0;
	}

	public void stopTurning() {
		curShipRotAcc = 0;
	}

	public boolean isDead() {
		return shipHealth <= 0;
	}

	public float getXPos() {
		return shipPos.x;
	}

	public float getYPos() {
		return shipPos.y;
	}

	public float getHealth() {
		return shipHealth;
	}

	public int getColour() {
		return SHIP_COLOUR;
	}

	public float getXVel() {
		return shipVel.x;
	}

	public float getYVel() {
		return shipVel.y;
	}

	public float getShipRad() {
		return SHIP_RAD;
	}

}
