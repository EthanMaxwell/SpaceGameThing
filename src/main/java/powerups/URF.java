package powerups;

import main.MainSketch;
import playerShip.PlayerShip;

public class URF extends PowerUp {

	public URF() {
		maxAge = 100;
		name = "Ultra Rapid Fire";
	}

	@Override
	public void apply(PlayerShip ship) {
		ship.modROF(1/6f);
		ship.modInaccur(2.1f);
		ship.modShotSize(1/1.3f);
		ship.modShotVel(1/1.4f);
		ship.modShotDam(1/3f);
	}

	@Override
	public void remove(PlayerShip ship) {
		ship.modROF(6f);
		ship.modInaccur(1/2.1f);
		ship.modShotSize(1.3f);
		ship.modShotVel(1.4f);
		ship.modShotDam(3f);
	}
}
