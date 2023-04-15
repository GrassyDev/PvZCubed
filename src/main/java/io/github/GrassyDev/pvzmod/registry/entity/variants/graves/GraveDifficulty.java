package io.github.GrassyDev.pvzmod.registry.entity.variants.graves;

import java.util.Arrays;
import java.util.Comparator;

public enum GraveDifficulty {
	NONE(0),
	EASY(1),
	EASYMED(2),
	MED(3),
	MEDHARD(4),
	HARD(5),
	SUPERHARD(6),
	NIGHTMARE(7),
	CRAAAZY(8);

	private static final GraveDifficulty[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(GraveDifficulty::getId)).toArray(GraveDifficulty[]::new);
	private final int id;

	GraveDifficulty(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static GraveDifficulty byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
