package enemies;

import objects.Shot;
import processing.core.PVector;

/**
 * A category of enemies that charge towards player, kamikaze style.
 * 
 * @author Ethan Maxwell
 *
 */
public abstract class Charger extends Enemy {

	/**
	 * Make new charger enemy at given x and y positions.
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	protected Charger(float x, float y) {
		super(x, y);

		speed = getSpeed();
	}

	@Override
	protected void modVelocity(PVector shipPos) {
		float angleToShip = (float) Math.atan((position.y - shipPos.y) / (position.x - shipPos.x));
		if (shipPos.x < position.x) {
			angleToShip += Math.PI;
		}
		
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
	
	@Override
	public Shot shoot() {
		return null;
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
