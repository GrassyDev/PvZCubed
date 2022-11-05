package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum FlagZombieVariants {
	DEFAULT(0),
	GAY(1),
	TRANS(2);

	private static final FlagZombieVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(FlagZombieVariants::getId)).toArray(FlagZombieVariants[]::new);
	private final int id;

	FlagZombieVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static FlagZombieVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
