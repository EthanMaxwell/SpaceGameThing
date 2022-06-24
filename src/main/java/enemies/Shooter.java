package enemies;

import objects.Shot;
import processing.core.PVector;

/**
 * A category of enemies that shoots the player from a distance, like a coward.
 * It will move faster when far from the player and try to maintain a fixed
 * distance.
 * 
 * @author Ethan Maxwell
 *
 */
public abstract class Shooter extends Enemy {

	/**
	 * Make new shooter enemy at given x and y positions.
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	protected Shooter(float x, float y) {
		super(x, y);
	}

	@Override
	protected void modVelocity(float targetX, float targetY) {
		float angleToShip = (float) Math.atan((position.y - targetY) / (position.x - targetX));
		if (targetX < position.x) {
			angleToShip += Math.PI;
		}

		angle = angleToShip;
		speed = Math.min(getMaxSpeed(), getBaseSpeed() + getDistSpeed() * position.dist(new PVector(targetX, targetY)));
	}

	@Override
	public Shot shoot() {
		if (shootTimer > getRateOfFire()) {
			shootTimer = 0;
			return new Shot(position.x, position.y, angle, getShotSpeed(), getShotDiameter(), true, getShotDamage());
		}
		return null;
	}

	/**
	 * @return The speed of this enemies shots
	 */
	abstract protected float getShotSpeed();

	/**
	 * @return The diameter (size) of this enemies shots
	 */
	abstract protected float getShotDiameter();

	/**
	 * @return The speed of this enemies shots
	 */
	abstract protected float getShotDamage();

	/**
	 * @return The rate of fire (frames per shot) of the enemy
	 */
	abstract protected float getRateOfFire();

	/**
	 * @return The max speed of the enemy
	 */
	abstract protected float getMaxSpeed();

	/**
	 * Get the base speed (before distance modifier) of the enemy. Should be
	 * negative so enemy maintains a distance from the player. The effect of this
	 * value is the smaller the value, the larger the distance this enemy maintains
	 * from the player.
	 * 
	 * @return The base speed of the enemy
	 */
	abstract protected float getBaseSpeed();

	/**
	 * Get the distance scaling of the speed (distance modifier) of the enemy.
	 * Should be positive so enemy moves towards the player. The effect of value is
	 * the larger this value is the faster this enemy will move overall, and make it
	 * move closer to the player.
	 * 
	 * @return The distance scaling of the speed (distance modifier) of the enemy
	 */
	abstract protected float getDistSpeed();
}
