package io.github.GrassyDev.pvzmod.registry.entity.variants.gears;

import java.util.Arrays;
import java.util.Comparator;

public enum StoneHelmetVariants {
	BRICK(0),
	TOWER(1),
	PYRAMID(2);

	private static final StoneHelmetVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(StoneHelmetVariants::getId)).toArray(StoneHelmetVariants[]::new);
	private final int id;

	StoneHelmetVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static StoneHelmetVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
