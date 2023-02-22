package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum NewspaperVariants {
	DEFAULT(0),
	DEFAULTHYPNO(1);

	private static final NewspaperVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(NewspaperVariants::getId)).toArray(NewspaperVariants[]::new);
	private final int id;

	NewspaperVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static NewspaperVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
