package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum BullyVariants {
	BULLY(0),
	BULLYHYPNO(1),
	BASKET(2),
	BASKETHYPNO(3);

	private static final BullyVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(BullyVariants::getId)).toArray(BullyVariants[]::new);
	private final int id;

	BullyVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static BullyVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
