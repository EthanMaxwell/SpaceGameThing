package enemies;

/**
 * A enemy that charges towards player, kamikaze style.
 * 
 * @author Ethan Maxwell
 *
 */
public abstract class Charger extends Enemy {

	/**
	 * Make new charger enemy at given x and y positions
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	protected Charger(float x, float y) {
		super(x, y);

		speed = getSpeed();
	}

	@Override
	protected void modVelocity(float angleToShip) {
		if (angle - angleToShip > Math.PI) {
			angle -= 2 * Math.PI;
		} else if (angleToShip - angle > Math.PI) {
			angle += 2 * Math.PI;
		}
		if (Math.abs(angleToShip - angle) > getAccuracy()) {
			if (angleToShip > angle) {
				angle += getTurnSpeed();
			} else {
				angle -= getTurnSpeed();
			}
		}
	}

	/**
	 * @return The turning speed of the enemy
	 */
	abstract protected float getTurnSpeed();

	/**
	 * @return The consistent speed of the enemy
	 */
	abstract protected float getSpeed();

	/**
	 * @return The accuracy of the enemy
	 */
	abstract protected float getAccuracy();

}
