package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum JetpackVariants {
	JETPACK(0),
	JETPACKHYPNO(1),
	BLASTRONAUT(2),
	BLASTRONAUTHYPNO(3);

	private static final JetpackVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(JetpackVariants::getId)).toArray(JetpackVariants[]::new);
	private final int id;

	JetpackVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static JetpackVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
