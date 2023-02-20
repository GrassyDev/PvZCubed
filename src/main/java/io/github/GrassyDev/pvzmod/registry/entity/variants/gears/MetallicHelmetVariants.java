package io.github.GrassyDev.pvzmod.registry.entity.variants.gears;

import java.util.Arrays;
import java.util.Comparator;

public enum MetallicHelmetVariants {
	BUCKET(0),
	MEDALLION(0),
	FOOTBALL(1),
	BERSERKER(2);

	private static final MetallicHelmetVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(MetallicHelmetVariants::getId)).toArray(MetallicHelmetVariants[]::new);
	private final int id;

	MetallicHelmetVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static MetallicHelmetVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
