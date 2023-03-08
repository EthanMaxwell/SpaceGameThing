package powerups;

import playerShip.PlayerShip;

/**
 * Power up that heals the players ship.
 * 
 * @author Ethan Maxwell
 */
public class HealingKit extends PowerUp {

	public HealingKit() {
		maxAge = 0;
		name = "Life-o-matic";
	}

	@Override
	public void apply(PlayerShip ship) {
		ship.damage(-10);
	}

	@Override
	public void remove(PlayerShip ship) {
		return;
	}

}
