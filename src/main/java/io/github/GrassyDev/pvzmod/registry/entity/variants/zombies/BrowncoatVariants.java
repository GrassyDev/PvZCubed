package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum BrowncoatVariants {
	DEFAULT(0),
	CONEHEAD(1),
	BUCKETHEAD(2),
	SCREENDOOR(3);

	private static final BrowncoatVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(BrowncoatVariants::getId)).toArray(BrowncoatVariants[]::new);
	private final int id;

	BrowncoatVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static BrowncoatVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
