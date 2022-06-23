package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enemies.Enemy;
import enemies.HeavyShooter;
import enemies.OvalShooter;
import enemies.SquareCharger;
import enemies.StarMine;
import enemies.TriangleCharger;

/**
 * Creates and stores the data for the types enemies quantities for each wave.
 * 
 * @author Ethan Maxwell
 */
public abstract class WaveData {
	/**
	 * Data for the types enemies quantities for each wave. List for each wave, each
	 * wave is a map form the class of enemy to it's quantity. Waves from last
	 * onwards should be last enter times wave number.
	 */
	public static List<Map<Class<? extends Enemy>, Integer>> waveData = WaveData.makeWavedata();

	/*
	 * Old data: 1{ 30, 0, 5, 0, 1 }, 2{ 30, 3, 10, 0, 3 }, 3{ 20, 8, 30, 0, 4 }, 4{
	 * 10, 12, 10, 1, 8 }, 5{ 5, 5, 5, 1, 33 }, 6{ 4, 10, 5, 3, 20 }, 7{ 3, 5, 5, 5,
	 * 20 }, 8{ 2, 5, 10, 8, 25 }, 9{ 1, 15, 30, 7, 30 }, 10{ 1, 1, 1, 1, 2 }, };
	 */

	/**
	 * Create the wave data.
	 * 
	 * @return The wave data created.
	 */
	private static List<Map<Class<? extends Enemy>, Integer>> makeWavedata() {
		List<Map<Class<? extends Enemy>, Integer>> waveData = new ArrayList<>();
		Map<Class<? extends Enemy>, Integer> wave;

		// Wave one
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 30);
		wave.put(StarMine.class, 5);
		waveData.add(wave);

		// Wave two
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 30);
		wave.put(TriangleCharger.class, 1);
		wave.put(StarMine.class, 10);
		wave.put(OvalShooter.class, 3);
		waveData.add(wave);

		// Wave three
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 20);
		wave.put(TriangleCharger.class, 4);
		wave.put(StarMine.class, 30);
		wave.put(OvalShooter.class, 8);
		waveData.add(wave);

		// Wave four
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 10);
		wave.put(TriangleCharger.class, 8);
		wave.put(StarMine.class, 10);
		wave.put(OvalShooter.class, 12);
		wave.put(HeavyShooter.class, 1);
		waveData.add(wave);

		// Wave five
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 5);
		wave.put(TriangleCharger.class, 50);
		wave.put(StarMine.class, 5);
		wave.put(OvalShooter.class, 2);
		wave.put(HeavyShooter.class, 1);
		waveData.add(wave);

		// Wave six
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 5);
		wave.put(TriangleCharger.class, 30);
		wave.put(StarMine.class, 5);
		wave.put(OvalShooter.class, 10);
		wave.put(HeavyShooter.class, 3);
		waveData.add(wave);

		// Wave seven
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 3);
		wave.put(TriangleCharger.class, 20);
		wave.put(StarMine.class, 5);
		wave.put(OvalShooter.class, 5);
		wave.put(HeavyShooter.class, 5);
		waveData.add(wave);

		// Wave eight
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 5);
		wave.put(TriangleCharger.class, 25);
		wave.put(StarMine.class, 5);
		wave.put(OvalShooter.class, 5);
		wave.put(HeavyShooter.class, 12);
		waveData.add(wave);

		// Wave nine
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 5);
		wave.put(TriangleCharger.class, 30);
		wave.put(StarMine.class, 50);
		wave.put(OvalShooter.class, 15);
		wave.put(HeavyShooter.class, 9);
		waveData.add(wave);

		// Repeating wave
		wave = new HashMap<>();
		wave.put(SquareCharger.class, 1);
		wave.put(TriangleCharger.class, 1);
		wave.put(StarMine.class, 2);
		wave.put(OvalShooter.class, 1);
		wave.put(HeavyShooter.class, 2);
		waveData.add(wave);

		return waveData;
	}
}
