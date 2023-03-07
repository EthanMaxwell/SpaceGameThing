package powerups;

import java.util.ArrayList;
import java.util.List;

import playerShip.PlayerShip;

public class PowerUpManager {
	private List<PowerUp> powerUps = new ArrayList<>();
	private PlayerShip ship;
	
	public PowerUpManager(PlayerShip ship) {
		this.ship = ship;
	}
	
	public void applyRandom() {
		PowerUp newPowerUp = new HealingKit();
		newPowerUp.apply(ship);
		powerUps.add(newPowerUp);
	}
	
	public void passTime() {
		for(int i = 0; i < powerUps.size(); i++) {
			if(powerUps.get(i).tooOld()) {
				powerUps.get(i).remove(ship);
				powerUps.remove(i);
				i--;
			}
		}
	}
}
