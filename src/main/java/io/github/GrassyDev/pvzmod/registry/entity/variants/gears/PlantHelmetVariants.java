package io.github.GrassyDev.pvzmod.registry.entity.variants.gears;

import java.util.Arrays;
import java.util.Comparator;

public enum PlantHelmetVariants {
	PUMPKIN(0);

	private static final PlantHelmetVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(PlantHelmetVariants::getId)).toArray(PlantHelmetVariants[]::new);
	private final int id;

	PlantHelmetVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PlantHelmetVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
