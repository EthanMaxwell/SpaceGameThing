package enemies;

import objects.Shot;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * A star shaped enemy that doesn't move, it just spins on the spot.
 * 
 * @author Ethan Maxwell
 *
 */
public class StarMine extends Enemy {

	/**
	 * Make new star shaped mine enemy at given x and y positions.
	 * 
	 * @param x Initial x position of the enemy
	 * @param y Initial y position of the enemy
	 */
	public StarMine(float x, float y) {
		super(x, y);
	}

	@Override
	protected float getSize() {
		return 40;
	}

	@Override
	protected int getMaxHealth() {
		return 5;
	}

	@Override
	public int getScore() {
		return 4;
	}

	@Override
	protected void drawEnemyShape(PApplet canvas) {
		// Draw a six pointed star
		canvas.triangle(0, getSize() / 2, (float) Math.sqrt(0.75) * getSize() / 2, -getSize() / 4,
				(float) (-Math.sqrt(0.75f) * getSize() / 2), -getSize() / 4);
		canvas.triangle(0, -getSize() / 2, (float) -Math.sqrt(0.75) * getSize() / 2, getSize() / 4,
				(float) Math.sqrt(0.75) * getSize() / 2, getSize() / 4);

	}

	@Override
	protected void modVelocity(PVector shipPos) {
		angle += 0.033f;
	}

	@Override
	public Shot shoot() {
		return null;
	}

}
