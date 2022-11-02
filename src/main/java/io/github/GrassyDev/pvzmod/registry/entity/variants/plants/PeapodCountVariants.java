package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum PeapodCountVariants {
	ONE(0),
	TWO(1),
	THREE(2),
	FOUR(3),
	FIVE(4);


	private static final PeapodCountVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(PeapodCountVariants::getId)).toArray(PeapodCountVariants[]::new);
	private final int id;

	PeapodCountVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PeapodCountVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
