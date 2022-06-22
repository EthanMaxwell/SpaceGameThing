package enemies;

import main.MainSketch;

/**
 * A fast triangle enemy that charges towards player, kamikaze style.
 * 
 * @author Ethan Maxwell
 *
 */
public class TriangleCharger extends Charger {

	/**
	 * Make new triangle enemy at given x and y positions.
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	protected TriangleCharger(float x, float y) {
		super(x, y);
	}

	@Override
	protected float getSize() {
		return 20;
	}

	@Override
	protected int getMaxHealth() {
		return 1;
	}

	@Override
	public int getScore() {
		return 2;
	}

	@Override
	protected float getTurnSpeed() {
		return 0.033f;
	}

	@Override
	protected float getSpeed() {
		return 8;
	}

	@Override
	protected float getAccuracy() {
		return 0.25f;
	}

	@Override
	public void drawEnemyShape(MainSketch canvas) {
		canvas.triangle(getSize() / 2, 0, -getSize() / 4, getSize() / 3, -getSize() / 4, -getSize() / 3);
	}
}
