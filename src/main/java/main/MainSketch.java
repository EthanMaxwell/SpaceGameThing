package main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import enemies.Enemy;
import enemies.StarMine;
import objects.Button;
import objects.Crate;
import objects.Flame;
import objects.Shot;
import playerShip.PlayerShip;
import powerups.PowerUp;
import processing.core.PApplet;
import processing.core.PVector;

public class MainSketch extends PApplet {
//All constants
	private static final float SCREEN_ACC = 0.15f, // Max acceleration of the screen
			SCREEN_DECEL = 0.96f, MOVE_SIZE = 0.35f, // Players % distance toward edge of screen before screen pans
			MM_SIZE = 0.2f, // Size of mini-map and % of world radius
			MM_DOT_SIZE = 5f, // Size on enemy and ally dots on the minimap
			CRATE_DIF = 0.35f, // % difference in crate number for different difficulties
			COLLISION_DAM = 5;

	private static final int WORLD_RAD = 1022, // Half the diameter of the world
			MM_GAP = 30, // Gap between mini-map and edge of screen
			EXPL_SIZE = 35, // Fire particles in an explosion
			WAVE_TIME = 1500, // Frames per wave aka speed it which enemies are spawned
			STAR_SIZE = 7, // Size of background stars
			BASE_CRATE_RATE = 120, // Default frames between crates spawning (modified by difficulty)
			MAX_CRATES = 13, // Maximum crate that can be on screen at any one time
			SCORE_POS = 280; // Distance between the players score and the right side of the screen
	static final int POWER_UP_TIME = 600; // Frames that a power up lasts for

//All variables for storing generic game data

	private PVector screenPos, // Position on whole map of the screen
			screenVel; // Velocities of the screen panning

	private int time, // Current game time in frames
			playerScore, // Players current game score
			gameScreen, // Current menu screen the player is in
			difficulty; // Difficulty from -1 (easy), 0 (normal) or 1 (hard)

	private boolean gameRunning = false; // Whether the actual game is running, or just menus

	private PVector[] stars = new PVector[250]; // The array of the positions of all the stars

	private PlayerShip playerShip;

	private ArrayList<Shot> shots; // All the players and enemy shots
	private ArrayList<Enemy> enemies; // All the enemies
	private ArrayList<Flame> flames; // All the flame from player thruster
	private ArrayList<Crate> crates; // All the crates across the map
	private ArrayList<PowerUp> powerUps; // All the crates across the map
	private ArrayList<Button> buttons = new ArrayList<Button>(); // All the button that appear in menus

	public static void main(String[] args) {
		String[] processingArgs = { "MainSketch" };
		MainSketch sketch = new MainSketch();
		MainSketch.runSketch(processingArgs, sketch);
	}

	public void settings() {
		fullScreen(); // Game runs best on fullscreen
		// TODO: Font problems...
		// textFont(loadFont("CenturyGothic-100.vlw")); // Use Century Gothic font
		restartGame(); // Inialise all the game values
		// Create all the random star positions
		for (int i = 0; i < stars.length; i++) {
			stars[i] = new PVector(random(-WORLD_RAD - 500, WORLD_RAD + 500),
					random(-WORLD_RAD - 500, WORLD_RAD + 500));
		}
		// Make all the buttons that will appear in the menus
		buttons.add(new Button("Start", 1f / 2, 1f / 2, 300, 140)); // Screen 0
		buttons.add(new Button("Easy", 1f / 4, 1f / 2, 250, 120)); // Screen 1
		buttons.add(new Button("Normal", 1f / 2, 1f / 2, 250, 120)); // Screen 1
		buttons.add(new Button("Hard", 3f / 4, 1f / 2, 250, 120)); // Screen 1
		buttons.add(new Button("Play again", 1f / 2, 4f / 6, 400, 120)); // Screen 2
		buttons.add(new Button("Exit", 1f / 2, 6f / 7, 150, 80)); // All screens
	}

	@Override
	public void draw() {

		background(0); // Make black background

		// Make a matrix for the modified object position based on the screen position
		pushMatrix();
		translate(screenPos.x, screenPos.y);

		drawStars(); // Draw all the stars

		// If the game is running the play the game
		if (gameRunning) {
			runGame();
		}
		// Otherwise just draw the flames, shots and enemies from the previous game
		else {
			drawFlames(); // Draw flames from previous game
			// Draw shots from previous game
			for (Shot curShot : shots) {
				curShot.drawShot(this);
			}
			// Draw enemies from previous game
			for (Enemy curEnemy : enemies) {
				curEnemy.drawEnemy(this);
			}
		}

		// Start placing object undepent on the screens position
		popMatrix();

		// If the game is running the draw the in game GUI, otherwise show a menu screen
		if (gameRunning) {
			showGui();
		} else {
			showMenu();
		}
	}

	private void runGame() {
		// Increment timers
		time++;

		// Create the enemies for the frame
		makeEnemies();
		// Create the crates for this frame
		if (time % (int) (BASE_CRATE_RATE + BASE_CRATE_RATE * difficulty * CRATE_DIF) == 0
				&& crates.size() < MAX_CRATES) {
			crates.add(new Crate(random(-WORLD_RAD, WORLD_RAD), random(-WORLD_RAD, WORLD_RAD)));
		}

		applyPowerUps(); // Apply power ups

		// Draw the border lines around the area the player can travel
		strokeWeight(5);
		stroke(255);
		noFill();
		rect(-WORLD_RAD, -WORLD_RAD, 2 * WORLD_RAD, 2 * WORLD_RAD);

		noStroke(); // Make sure all object are displayed without a stroke

		setScreenVel(); // Set the velocity of the screen
		modCrates(); // Do all operations to do with crates
		drawFlames(); // Draw all the flames
		modShots(); // Do all operations to do with shots
		playerShip.modShip(WORLD_RAD); // Do all operations to do with the players ship
		playerShip.drawShip(screenPos, this);
		modEnemies(); // DO all operations to do with enemies
		screenPos.add(screenVel); // Move the screen

		// Make they player shoot is they have clicked and they have reloaded
		if (mousePressed) {
			Shot newShot = playerShip.shoot(mouseX, mouseY, screenPos);
			if (newShot != null)
				shots.add(newShot);
		}

		// Check if the player has died, and if so end the game
		if (playerShip.isDead()) {
			gameRunning = false; // Stop the game from running
			makeExplosion(playerShip.getXPos(), playerShip.getYPos()); // Make players ship explode
		}
	}

	/**
	 * Draws a menu screen for the players to click buttons on Game name always
	 * appears at the top with the buttons and text below it It has three screens
	 * 0:Start menu, 1:Difficulty select, 2:End game screen
	 */
	private void showMenu() {
		textAlign(CENTER, CENTER); // The menu use a different text align
		fill(180, 180, 180); // Text on screen appears a grey
		textSize(100); // Size of the title text
		text("Space Survival Warfare", width / 2, 110); // Draw the title

		// Game screen 0:Start menu
		if (gameScreen == 0) {
			buttons.get(0).drawButton(this); // Draw start button
		}
		// Game screen 1:Difficulty select
		else if (gameScreen == 1) {
			buttons.get(1).drawButton(this); // Draw easy button
			buttons.get(2).drawButton(this); // Draw normal button
			buttons.get(3).drawButton(this); // Draw hard button
		}
		// Game screen 2:End game
		else {
			textSize(50); // Text curSize on end game information
			// Write out all the end game information

			text("You got a score of : " + playerScore, width / 2, height / 3);
			// Easy difficulty message
			if (difficulty == -1) {
				text("On easy difficulty", width / 2, height / 2);
			}
			// Normal difficulty message
			else if (difficulty == 0) {
				text("On normal difficulty", width / 2, height / 2);
			}
			// Hard difficulty message
			else {
				text("On hard difficulty", width / 2, height / 2);
			}

			buttons.get(4).drawButton(this); // Draw play again button
		}

		buttons.get(5).drawButton(this); // Always draw exit button on all screens
	}

	/**
	 * Draws out the in game GUI for the player Includes health bar, score, minimap
	 * and current power ups
	 */
	private void showGui() {
		textAlign(LEFT); // Gui uses on text align
		noStroke();

		fill(20, 240, 20); // Green colour of the health bar
		rect(10, 10, playerShip.getHealth() * 12, 20); // Health bar

		// Draw all enemies except mines onto minimap area
		for (Enemy curEnemy : enemies) {
			fill(255, 10, 10); // Red for enemy dots on minimap
			if (!(curEnemy instanceof StarMine)) { // If not a mine draw the dot
				ellipse(width - WORLD_RAD / 2 * MM_SIZE - MM_GAP + curEnemy.getPosX() / 2 * MM_SIZE,
						height - WORLD_RAD / 2 * MM_SIZE - MM_GAP + curEnemy.getPosY() / 2 * MM_SIZE, MM_DOT_SIZE,
						MM_DOT_SIZE);
			}
		}

		// Draw player ship on minimap
		fill(playerShip.getColour());
		ellipse(width - WORLD_RAD / 2 * MM_SIZE - MM_GAP + playerShip.getXPos() / 2 * MM_SIZE,
				height - WORLD_RAD / 2 * MM_SIZE - MM_GAP + playerShip.getYPos() / 2 * MM_SIZE, MM_DOT_SIZE,
				MM_DOT_SIZE);

		// nonfilled white lines
		noFill();
		stroke(255);
		// Draw the box to show players view on minimap
		strokeWeight(1);
		rect(width - WORLD_RAD / 2 * MM_SIZE - MM_GAP - screenPos.x / 2 * MM_SIZE,
				height - WORLD_RAD / 2 * MM_SIZE - MM_GAP - screenPos.y / 2 * MM_SIZE, width / 2 * MM_SIZE,
				height / 2 * MM_SIZE);
		// Draw box around minimap
		strokeWeight(5);
		rect(width - WORLD_RAD * MM_SIZE - MM_GAP, height - WORLD_RAD * MM_SIZE - MM_GAP, WORLD_RAD * MM_SIZE,
				WORLD_RAD * MM_SIZE);

		showPowerUps(); // Draw power up onto GUI

		// Show players score
		textSize(50);
		text("Score : " + playerScore, width - SCORE_POS, 40);
	}

	/**
	 * When passed a shot will check if that shot is touching an enemy Will damage,
	 * and if applicable remove any ships the shot is in contact with
	 */
	private boolean touchingEn(Shot curShot) {
		// Cycle through an check all enemies
		for (int i = enemies.size() - 1; i >= 0; i--) {
			if (curShot.touching(enemies.get(i).getPosX(), enemies.get(i).getPosY(), 30, false)) {
				if (enemies.get(i).damage(curShot.getDamage())) { // Check if enemy was destroyed by shots
					makeExplosion(enemies.get(i).getPosX(), enemies.get(i).getPosY()); // Make enemy explode
					playerScore += enemies.get(i).getScore(); // Give the player the score
					enemies.remove(i);
				}
				return true; // The shot has hit an enemy
			}
		}
		return false;
	}

	/*
	 * Makes all the enemies over time for the player to fight Extracts it data from
	 * the WaveData It creates specified quantity of enemies of the time of the wave
	 */
	private void makeEnemies() {
		int waveNum = time / WAVE_TIME; // Extract the wave number from the time
		int waveTime = (time + 1) % WAVE_TIME;// Extract time through the current wave for time
		Map<Class<? extends Enemy>, Integer> waveData = WaveData.waveData
				.get(Math.min(waveNum, WaveData.waveData.size() - 1));

		// Spawn appropriate enemies for this frame
		for (Class<? extends Enemy> enemyClass : waveData.keySet()) {
			int spawnRate;
			// For last set of data onwards make spawn rate last wave values * wave num
			if (waveNum >= WaveData.waveData.size() - 1) {
				spawnRate = waveData.get(enemyClass) * waveNum;
			}
			// Otherwise extract spawn rate normally
			else {
				spawnRate = waveData.get(enemyClass);
			}

			// Check if it's that enemies frame to be spawned
			if (spawnRate != 0 && waveTime % (WAVE_TIME / spawnRate) == WAVE_TIME / (2 * spawnRate)) {
				try {
					// Create new enemy through reflection
					enemies.add(enemyClass.getConstructor(float.class, float.class)
							.newInstance(random(-WORLD_RAD, WORLD_RAD), random(-WORLD_RAD, WORLD_RAD)));
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					// Should be unreachable
					throw new RuntimeException("Class " + enemyClass + " does not have valid constuctor...");
				}
			}
		}
	}

//Makes an explosion (using flames) at the specified location
	private void makeExplosion(float xPos, float yPos) {
		for (int j = 0; j < EXPL_SIZE; j++) {
			flames.add(new Flame(xPos, yPos, random(-PI, PI))); // New flame pointing in random direction
		}
	}

	/*
	 * Draw all the flames, if they are past their life span, remove them Also
	 * create new flames for the thruster of the player is accelerating
	 */
	private void drawFlames() {
		// Make new flames for the ships thrusher if accelerating while the game is
		// running
		if (gameRunning) {
			Flame newFlame = playerShip.drawThruster();
			if (newFlame != null)
				flames.add(newFlame);
		}

		// Draw all the flames, removing the ones that are to old
		for (int i = flames.size() - 1; i >= 0; i--) {
			flames.get(i).drawFlame(this);
			if (flames.get(i).getAge() <= 0) {
				flames.remove(i);
			}
		}
	}

	/*
	 * The sets the screen velocity so that it moves with the player It does this
	 * through making the screen accelerate to the velocity to the player's ship if
	 * it’s too close to the edge. Otherwise the screen decelerates back to a
	 * stop.
	 */
	private void setScreenVel() {
		// X CALCULATIONS
		// Check if the player is too close to the right side of the screen
		if (playerShip.getXPos() + screenPos.x > width * (1 - MOVE_SIZE)) {
			if (-screenVel.x <= playerShip.getXVel()) {// Stop accelerating if higher velocity then player
				screenVel.x -= SCREEN_ACC;
			}
		}
		// Check if the player is too close to the left side of the screen
		else if (playerShip.getXPos() + screenPos.x < width * MOVE_SIZE) {
			if (-screenVel.x >= playerShip.getXVel()) {// Stop accelerating if higher velocity then player
				screenVel.x += SCREEN_ACC;
			}
		}
		// If the player is not near the edge of the screen, decelerate the screen
		else {
			screenVel.x = screenVel.x * SCREEN_DECEL;
		}

		// Y CALCULATIONS
		// Check if the player is too close to the bottom of the screen
		if (playerShip.getYPos() + screenPos.y > height * (1 - MOVE_SIZE)) {
			if (-screenVel.y <= playerShip.getYVel()) {// Stop accelerating if higher velocity then player
				screenVel.y -= SCREEN_ACC;
			}
		}
		// Check if the player is too close to the top of the screen
		else if (playerShip.getYPos() + screenPos.y < height * MOVE_SIZE) {
			if (-screenVel.y >= playerShip.getYVel()) {// Stop accelerating if higher velocity then player
				screenVel.y += SCREEN_ACC;
			}
		}
		// If the player is not near the edge of the screen, decelerate the screen
		else {
			screenVel.y = screenVel.y * SCREEN_DECEL;
		}
	}

	/*
	 * Do everything to do with the crates This includes drawing them and checking
	 * if they are on the player then remove it and applying the power up
	 */
	private void modCrates() {
		for (int i = crates.size() - 1; i >= 0; i--) {
			// Check if touching player, is so, then remove it and apply power up
			if (crates.get(i).touching(playerShip.getXPos(), playerShip.getYPos(), playerShip.getShipRad())) {
				powerUps.add(new PowerUp()); // Apply a random power up
				crates.remove(i);
			} else {
				crates.get(i).drawCrate(this);
			}
		}
	}

	/*
	 * Do everything to do with all shots, friendly or hostile If it's touching an
	 * enemy or the player ship the correct damage and remove it Also if the shot is
	 * offscreen remove it as well
	 */
	private void modShots() {
		for (int i = shots.size() - 1; i >= 0; i--) {
			// Check if the shot has collided with the player
			if (shots.get(i).touching(playerShip.getXPos(), playerShip.getYPos(), playerShip.getShipRad(), true)) {
				// Damage the player and remove the shot
				playerShip.damage(shots.get(i).getDamage());
				shots.remove(i);
			}
			// Check if the shot has collided with a enemy, remove it if it has
			else if (touchingEn(shots.get(i))) {
				shots.remove(i);
			}
			// Check if the shot is off the screen
			else if (shots.get(i).notOnScreen(screenPos, width, height)) {
				shots.remove(i);
			}
			// If it no removed then move and draw it
			else {
				shots.get(i).moveShot();
				shots.get(i).drawShot(this);
			}
		}
	}

	/*
	 * Do everything to do with enemies, This includes moving them, checking if
	 * they're touching the ship then removing them and damaging the player
	 * Otherwise draw the enemy and make them shoot
	 */
	private void modEnemies() {
		for (int i = enemies.size() - 1; i >= 0; i--) {
			enemies.get(i).moveEnemy(playerShip.getXPos(), playerShip.getYPos(), difficulty);// Move enemy
			// Check if the enemy has collided with the player
			if (enemies.get(i).touching(playerShip.getXPos(), playerShip.getYPos(), playerShip.getShipRad())) {
				makeExplosion(enemies.get(i).getPosX(), enemies.get(i).getPosY());
				enemies.remove(i);
				playerShip.damage(COLLISION_DAM);
			}
			// Otherwise draw it and make it shoot
			else {
				enemies.get(i).drawEnemy(this);
				Shot newShot = enemies.get(i).shoot();
				if (newShot != null)
					shots.add(newShot);
			}
		}
	}

//Draw all the stars
	private void drawStars() {
		fill(255); // White colour of the stars
		noStroke();
		for (PVector curStar : stars) {
			ellipse(curStar.x, curStar.y, STAR_SIZE, STAR_SIZE);
		}
	}

//Applies all the current power ups to they player
	private void applyPowerUps() {
		playerShip.applyPowerUps(powerUps);
		// Cycle through the power ups to apply them, remove finished ones
		for (int i = powerUps.size() - 1; i >= 0; i--) {
			System.out.println(powerUps.get(i).age());
			if (powerUps.get(i).age() > POWER_UP_TIME) {
				powerUps.remove(i);
			}
		}
	}

//Draw all the current power up to the screen
	private void showPowerUps() {
		fill(255);// White
		textSize(30);// power up text curSize
		// The power ups need to know what number they are to be drawn
		int powerUpNum = 0;
		// Cycle through all the power up so they can be drawn
		for (PowerUp curPowerUp : powerUps) {
			curPowerUp.drawPowerUp(POWER_UP_TIME, powerUpNum, this);
			powerUpNum++; // track the number of the power up
		}
	}

	/*
	 * Initialise all the values to their original state To be used to start/restart
	 * the game before it's run each time
	 */
	private void restartGame() {
		shots = new ArrayList<Shot>();
		enemies = new ArrayList<Enemy>();
		flames = new ArrayList<Flame>();
		crates = new ArrayList<Crate>();
		powerUps = new ArrayList<PowerUp>();
		screenPos = new PVector(width / 2, height / 2);
		playerShip = new PlayerShip(difficulty);
		screenVel = new PVector(0, 0);
		playerScore = 0;
		time = WAVE_TIME * (difficulty + 1);
	}

	/*
	 * Run when the player just clicks the mouse Not used in game, only in the menus
	 */
	public void mouseReleased() {
		if (!gameRunning) {
			// If in the main menu check for appropriate button pressed
			if (gameScreen == 0) {
				// Check if start button was pressed
				if (buttons.get(0).mouseOnButton(this)) {
					gameScreen++;
				}
			}

			// If in the difficulty menu check for appropriate button pressed
			else if (gameScreen == 1) {
				// Check if any of the difficulty buttons was pressed
				for (int i = 0; i < 3; i++) {
					if (buttons.get(i + 1).mouseOnButton(this)) {
						gameScreen++;
						difficulty = i - 1;
						gameRunning = true;
						restartGame();
					}
				}
			}

			// If it not one of the other screen, to must be end game screen
			else {
				// Check if the play again button was pressed
				if (buttons.get(4).mouseOnButton(this)) {
					gameScreen = 1;
				}
			}
			if (buttons.get(5).mouseOnButton(this)) {
				exit();
			}
		}
	}

//The player just started holding down a key, so make the correct action if game is running
	public void keyPressed() {
		if (gameRunning) { // First ensure the game is actually running
			// Player pressed forward button, so acceleration
			if (key == 'w') {
				playerShip.accelerate();
			}
			// Player pressed left button, so give anticlockwise rotational acceleration
			else if (key == 'a') {
				playerShip.turnLeft();
			}
			// Player pressed right button, so give clockwise rotational acceleration
			else if (key == 'd') {
				playerShip.turnRight();
			}
		}
	}

//When the player stops pressing a button make an action
	public void keyReleased() {
		// The player stopped pressing forwards, so stop accelerating
		if (key == 'w') {
			playerShip.stop();
		}
		// The player stopped pressing left or right, so stop turning
		else if (key == 'a' || key == 'd') {
			playerShip.stopTurning();
		}
	}
}