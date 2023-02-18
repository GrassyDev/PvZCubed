package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum SuperFanImpVariants {
	DEFAULT(0),
	NEWYEAR(1);

	private static final SuperFanImpVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(SuperFanImpVariants::getId)).toArray(SuperFanImpVariants[]::new);
	private final int id;

	SuperFanImpVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static SuperFanImpVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
