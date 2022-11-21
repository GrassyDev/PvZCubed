package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum TwinSunflowerVariants {
	DEFAULT(0),
	LESBIAN(1),
	WLW(2),
	MLM(3),
	WLW_MLM(4),
	LESBIAN_WLW(5);

	private static final TwinSunflowerVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(TwinSunflowerVariants::getId)).toArray(TwinSunflowerVariants[]::new);
	private final int id;

	TwinSunflowerVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static TwinSunflowerVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
