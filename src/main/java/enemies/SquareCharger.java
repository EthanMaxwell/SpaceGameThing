package enemies;

import main.MainSketch;
import objects.Shot;

/**
 * A slow square enemy that charges towards player, kamikaze style.
 * 
 * @author Ethan Maxwell
 *
 */
public class SquareCharger extends Charger {

	/**
	 * Make new square enemy at given x and y positions
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	protected SquareCharger(float x, float y) {
		super(x, y);
	}

	@Override
	protected float getSize() {
		return 15;
	}

	@Override
	protected int getMaxHealth() {
		return 1;
	}

	@Override
	public int getScore() {
		return 1;
	}

	@Override
	protected float getTurnSpeed() {
		return 0.05f;
	}

	@Override
	protected float getSpeed() {
		return 4;
	}

	@Override
	protected float getAccuracy() {
		return 0.5f;
	}

	@Override
	public void drawEnemyShape(MainSketch canvas) {
		canvas.rect(-getSize() / 2, -getSize() / 2, getSize(), getSize());

	}

	@Override
	public Shot shoot() {
		return null;
	}
}
