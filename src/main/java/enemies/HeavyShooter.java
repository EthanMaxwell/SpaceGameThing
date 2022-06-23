package enemies;

import main.MainSketch;
import processing.core.PConstants;

/**
 * A flat nosed triangle shaped enemy that rapidly shoots bullets at the player
 * from a distance.
 * 
 * @author Ethan Maxwell
 *
 */
public class HeavyShooter extends Shooter {

	/**
	 * Make new heavy shooter enemy at given x and y positions.
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	public HeavyShooter(float x, float y) {
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
		return 5;
	}

	@Override
	protected float getShotSpeed() {
		return 7;
	}

	@Override
	protected float getShotDiameter() {
		return 17;
	}

	@Override
	protected float getShotDamage() {
		return 2;
	}

	@Override
	protected float getRateOfFire() {
		return 21;
	}

	@Override
	protected float getMaxSpeed() {
		return 9;
	}

	@Override
	protected float getBaseSpeed() {
		return -6;
	}

	@Override
	protected float getDistSpeed() {
		return 0.02f;
	}

	@Override
	protected void drawEnemyShape(MainSketch canvas) {
		// Draw a flat nosed triangle like shape
		canvas.beginShape();
		canvas.vertex(getSize() / 2, getSize() / 3);
		canvas.vertex(-getSize() / 2, getSize() / 2);
		canvas.vertex(-getSize() / 4, 0);
		canvas.vertex(-getSize() / 2, -getSize() / 2);
		canvas.vertex(getSize() / 2, -getSize() / 3);
		canvas.endShape(PConstants.CLOSE);
	}

}
