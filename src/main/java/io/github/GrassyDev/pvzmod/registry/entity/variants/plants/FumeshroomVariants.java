package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum FumeshroomVariants  {
	DEFAULT(0),
	GAY(1),
	TRANS(2);

	private static final FumeshroomVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(FumeshroomVariants::getId)).toArray(FumeshroomVariants[]::new);
	private final int id;

	FumeshroomVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static FumeshroomVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
