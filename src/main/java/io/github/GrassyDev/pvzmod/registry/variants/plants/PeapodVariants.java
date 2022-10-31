package io.github.GrassyDev.pvzmod.registry.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum PeapodVariants {
	DEFAULT(0),
	PLURAL(1);

	private static final PeapodVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(PeapodVariants::getId)).toArray(PeapodVariants[]::new);
	private final int id;

	PeapodVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static PeapodVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
