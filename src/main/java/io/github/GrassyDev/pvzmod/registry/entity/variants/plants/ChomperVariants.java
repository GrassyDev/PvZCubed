package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum ChomperVariants {
	DEFAULT(0),
	DEMIGIRL(1),
	ENBY(2),
	PIRANHAPLANT(3);

	private static final ChomperVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ChomperVariants::getId)).toArray(ChomperVariants[]::new);
	private final int id;

	ChomperVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ChomperVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
