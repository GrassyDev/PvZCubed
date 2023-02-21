package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum GargantuarVariants {
	GARGANTUAR(0),
	GARGANTUARHYPNO(1),
	DEFENSIVEEND(2),
	DEFENSIVEENDHYPNO(3),
	DEFENSIVEEND_NEWYEAR(4),
	DEFENSIVEEND_NEWYEARHYPNO(5);

	private static final GargantuarVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(GargantuarVariants::getId)).toArray(GargantuarVariants[]::new);
	private final int id;

	GargantuarVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static GargantuarVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
