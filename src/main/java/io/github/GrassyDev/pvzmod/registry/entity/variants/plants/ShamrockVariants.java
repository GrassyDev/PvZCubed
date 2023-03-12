package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum ShamrockVariants {
	DEFAULT(0),
	PRIDE(1);

	private static final ShamrockVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ShamrockVariants::getId)).toArray(ShamrockVariants[]::new);
	private final int id;

	ShamrockVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ShamrockVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
