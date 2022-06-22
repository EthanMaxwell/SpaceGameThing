package enemies;

import main.MainSketch;

/**
 * A oval shaped enemy that shoots light bullets at the player from a
 * distance.
 * 
 * @author Ethan Maxwell
 *
 */
public class OvalShooter extends Shooter {

	/**
	 * Make new oval shooter enemy at given x and y positions.
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	protected OvalShooter(float x, float y) {
		super(x, y);
	}

	@Override
	protected float getSize() {
		return 30;
	}

	@Override
	protected int getMaxHealth() {
		return 2;
	}

	@Override
	public int getScore() {
		return 3;
	}

	@Override
	protected float getShotSpeed() {
		return 11;
	}

	@Override
	protected float getShotDiameter() {
		return 12;
	}

	@Override
	protected float getShotDamage() {
		return 3;
	}

	@Override
	protected float getRateOfFire() {
		return 77;
	}

	@Override
	protected float getMaxSpeed() {
		return 5;
	}

	@Override
	protected float getBaseSpeed() {
		return -2;
	}

	@Override
	protected float getDistSpeed() {
		return 0.0225f;
	}

	@Override
	protected void drawEnemyShape(MainSketch canvas) {
		canvas.ellipse(0, 0, getSize(), getSize() * 0.7f);
	}
}
