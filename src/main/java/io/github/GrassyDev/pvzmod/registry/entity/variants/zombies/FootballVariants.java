package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum FootballVariants {
	DEFAULT(0),
	BERSERKER(1),
	FOOTBALLHYPNO(2),
	BERSERKERHYPNO(3);

	private static final FootballVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(FootballVariants::getId)).toArray(FootballVariants[]::new);
	private final int id;

	FootballVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static FootballVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
