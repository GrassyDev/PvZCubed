package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum PharaohVariants {
	UNDYING(0),
	SUMMONED(1),
	UNDYINGHYPNO(2),
	SUMMONEDHYPNO(3);

	private static final PharaohVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(PharaohVariants::getId)).toArray(PharaohVariants[]::new);
	private final int id;

	PharaohVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PharaohVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
