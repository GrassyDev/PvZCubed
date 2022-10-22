package io.github.GrassyDev.pvzmod.registry.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum ScaredyshroomVariants {
	DEFAULT(0),
	DEMIBOY(1),
	LINK(2);

	private static final ScaredyshroomVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ScaredyshroomVariants::getId)).toArray(ScaredyshroomVariants[]::new);
	private final int id;

	ScaredyshroomVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ScaredyshroomVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
