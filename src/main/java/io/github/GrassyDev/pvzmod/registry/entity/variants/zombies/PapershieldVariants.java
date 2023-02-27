package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum PapershieldVariants {
	NEWSPAPERSHIELD(0),
	SUNDAYEDITIONSHIELD(1);

	private static final PapershieldVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(PapershieldVariants::getId)).toArray(PapershieldVariants[]::new);
	private final int id;

	PapershieldVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PapershieldVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
