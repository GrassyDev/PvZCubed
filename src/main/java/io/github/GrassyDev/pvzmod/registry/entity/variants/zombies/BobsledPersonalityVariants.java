package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum BobsledPersonalityVariants {
	DEFAULT(0),
	LEADER(1),
	MOVER(2),
	YOUNG(3);

	private static final BobsledPersonalityVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(BobsledPersonalityVariants::getId)).toArray(BobsledPersonalityVariants[]::new);
	private final int id;

	BobsledPersonalityVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static BobsledPersonalityVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
