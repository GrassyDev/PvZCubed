package io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles;

import java.util.Arrays;
import java.util.Comparator;

public enum FumeVariants {
	DEFAULT(0),
	GAY(1),
	TRANS(2);

	private static final FumeVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(FumeVariants::getId)).toArray(FumeVariants[]::new);
	private final int id;

	FumeVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static FumeVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
