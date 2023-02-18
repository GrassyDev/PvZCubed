package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum DefensiveEndVariants {
	DEFAULT(0),
	NEWYEARIMP(1);

	private static final DefensiveEndVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(DefensiveEndVariants::getId)).toArray(DefensiveEndVariants[]::new);
	private final int id;

	DefensiveEndVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static DefensiveEndVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
