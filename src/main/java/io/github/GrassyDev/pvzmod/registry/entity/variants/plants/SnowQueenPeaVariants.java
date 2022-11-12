package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum SnowQueenPeaVariants {
	DEFAULT(0),
	LESBIAN(1),
	BISEXUAL(2);

	private static final SnowQueenPeaVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(SnowQueenPeaVariants::getId)).toArray(SnowQueenPeaVariants[]::new);
	private final int id;

	SnowQueenPeaVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static SnowQueenPeaVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
