package powerups;

import playerShip.PlayerShip;
import processing.core.PApplet;

public abstract class PowerUp {

	/**enum Type {
		HealingKit("Healing Kit"), SniperShot("Sniper Shot"), RapidFire("Rapid Fire"), HeavyShot("Heavy Shot"),
		URF("Ultra Rapid Fire");

		private String powerUpName;

		private Type(String name) {
			powerUpName = name;
		}
	}*/

	private int age = 0;
	protected int maxAge;
	private String name;
	//private Type type = Type.values()[(int) (Math.random() * Type.values().length)];
	
	/**
	 * Draw the text in the bottom left to show the time left on the power up
	 * Position is based on the number power up it is Time remaining is shown in
	 * approximately seconds (depends on frame rate)
	 * @param powerUpNum2 
	 * 
	 * @param canvas
	 */
	public void drawPowerUp(int powerUpTime, int powerUpNum, PApplet canvas) {
		canvas.text(name + " " + ((powerUpTime - age) / canvas.frameRate + 1), 15, canvas.height - 30 * powerUpNum - 10);
	}
	
	public boolean tooOld() {
		return ++age > maxAge;
	}
	
	protected abstract void apply(PlayerShip ship);
	
	public abstract void remove(PlayerShip ship);
}
