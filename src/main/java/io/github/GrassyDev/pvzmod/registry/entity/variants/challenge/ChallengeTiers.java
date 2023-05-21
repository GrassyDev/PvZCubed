package io.github.GrassyDev.pvzmod.registry.entity.variants.challenge;

import java.util.Arrays;
import java.util.Comparator;

public enum ChallengeTiers {
	ONE(0),
	TWO(1),
	THREE(2),
	FOUR(3),
	FIVE(4),
	SIX(5),
	SEVEN(6),
	EIGHT(7),
	NINE(8);

	private static final ChallengeTiers[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ChallengeTiers::getId)).toArray(ChallengeTiers[]::new);
	private final int id;

	ChallengeTiers(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ChallengeTiers byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
