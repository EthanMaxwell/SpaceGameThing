package objects;
import main.MainSketch;
import processing.core.PApplet;

//TODO: Whole class is a dumpster fire, so please fix. Should have sub classes and not all be static, no Applet class.
/**
 * The power ups that the player can get 0: Healing kit heals player ship per
 * frame 1: Sniper shot increase accuracy and shot velocity while giving a bit
 * more damage 2: Rapid fire greatly increases the players ships rate of fire 3:
 * Heavy shot increase the size on the players shot and greatly increase their
 * damage 4: Makes the player shot very fast, by decreases accuracy and shot
 * size
 */
public class PowerUp {
	// Names of all the different possible power ups
	static final String[] POWER_UP_NAMES = { "Healing kit", "Sniper shot", "Rapid fire", "Heavy bullets",
			"Ultra Rapid Fire" };

	static final int POWER_UP_TIME = 600; // Frames that a power up lasts for

	int powerUpType; // The number type of a power up
	int powerUpTime = POWER_UP_TIME; // Frames remaining on the power up

	// Create a new power up on the given type
	public PowerUp(int powerUpNum) {
		powerUpType = powerUpNum;
	}

	// TODO: This is a bad way to apply power ups. Make ship stats class and just
	// run it through the modifiers.
	/**
	 * Applies the power up to the players ship Returns whether the power up has run
	 * out
	 */
	public boolean powerUpNum() {
		powerUpTime--; // Countdown the time left on the power up

		// Healing kit heals player ship per frame
		if (powerUpType == 0) {
			MainSketch.shipHealth += 0.022;
		}

		// Sniper shot increase accuracy and shot velocity while giving a bit more
		// damage
		else if (powerUpType == 1) {
			MainSketch.curShotInacu /= 10;
			MainSketch.curShotVel *= 1.65;
			MainSketch.curShotDamage *= 2;
		}

		// Rapid fire greatly increases the players ships rate of fire
		else if (powerUpType == 2) {
			MainSketch.curShotTime /= 1.8;
		}

		// Heavy shot increase the size on the players shot and greatly increase their
		// damage
		else if (powerUpType == 3) {
			MainSketch.curShotSize += 6;
			MainSketch.curShotDamage += 5;
		}

		// Makes the player shot very fast, by decreases accuracy, size, speed and
		// damage
		else if (powerUpType == 4) {
			MainSketch.curShotTime /= 6;
			MainSketch.curShotInacu *= 2.1;
			MainSketch.curShotSize /= 1.3;
			MainSketch.curShotVel /= 1.4;
			MainSketch.curShotDamage /= 3;
		}

		return powerUpTime < 0;
	}

	/**
	 * Draw the text in the bottom left to show the time left on the power up
	 * Position is based on the number power up it is Time remaining is shown in
	 * approximately seconds (depends on frame rate)
	 * 
	 * @param canvas
	 */
	public void drawPowerUp(int powerUpNum, MainSketch canvas) {
		// TODO:Remove
		canvas.text(POWER_UP_NAMES[powerUpType] + " " + (powerUpTime / 60 + 1), 15, canvas.height - 30 * powerUpNum - 10);
	}
}
