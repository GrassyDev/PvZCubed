package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum SnowPeaVariants {
	DEFAULT(0),
	MLM(1),
	BISEXUAL(2);

	private static final SnowPeaVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(SnowPeaVariants::getId)).toArray(SnowPeaVariants[]::new);
	private final int id;

	SnowPeaVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static SnowPeaVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
