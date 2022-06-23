package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enemies.Enemy;
import enemies.OvalShooter;
import enemies.SquareCharger;
import enemies.StarMine;
import enemies.TriangleCharger;

public abstract class WaveData {
	public static List<Map<Class<? extends Enemy>, Integer>> waveData = WaveData.makeWavedata();

	/*
	 * { 30, 0, 5, 0, 1 }, { 30, 3, 10, 0, 3 }, { 20, 8, 30, 0, 4 }, { 10, 12, 10,
	 * 1, 8 }, { 5, 5, 5, 1, 33 }, { 4, 10, 5, 3, 20 }, { 3, 5, 5, 5, 20 }, { 2, 5,
	 * 10, 8, 25 }, { 1, 15, 30, 7, 30 }, { 1, 1, 1, 1, 2 }, };
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

		return waveData;
	}
}
