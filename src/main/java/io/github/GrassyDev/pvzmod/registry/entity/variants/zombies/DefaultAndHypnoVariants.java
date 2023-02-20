package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum DefaultAndHypnoVariants {
	DEFAULT(0),
	HYPNO(1);

	private static final DefaultAndHypnoVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(DefaultAndHypnoVariants::getId)).toArray(DefaultAndHypnoVariants[]::new);
	private final int id;

	DefaultAndHypnoVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static DefaultAndHypnoVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
