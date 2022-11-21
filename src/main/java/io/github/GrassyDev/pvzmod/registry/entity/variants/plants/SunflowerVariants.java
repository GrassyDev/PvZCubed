package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum SunflowerVariants {
	DEFAULT(0),
	LESBIAN(1),
	WLW(2),
	MLM(3);

	private static final SunflowerVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(SunflowerVariants::getId)).toArray(SunflowerVariants[]::new);
	private final int id;

	SunflowerVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static SunflowerVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
