package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum TulipVariants {
	DEFAULT(0),
	HYPNO(1),
	HEAL(2);

	private static final TulipVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(TulipVariants::getId)).toArray(TulipVariants[]::new);
	private final int id;

	TulipVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static TulipVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
