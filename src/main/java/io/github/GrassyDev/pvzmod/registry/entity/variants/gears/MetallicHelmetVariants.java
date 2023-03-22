package io.github.GrassyDev.pvzmod.registry.entity.variants.gears;

import java.util.Arrays;
import java.util.Comparator;

public enum MetallicHelmetVariants {
	BUCKET(0),
	MEDALLION(1),
	FOOTBALL(2),
	DEFENSIVEEND(3),
	BERSERKER(4),
	BLASTRONAUT(5),
	KNIGHT(6);

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
