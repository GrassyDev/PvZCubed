package io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles;

import java.util.Arrays;
import java.util.Comparator;

public enum ShootingPeaVariants {
	DEFAULT(0),
	BLACK(1),
	PURPLE(2),
	BLUE(3),
	CYAN(4);

	private static final ShootingPeaVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ShootingPeaVariants::getId)).toArray(ShootingPeaVariants[]::new);
	private final int id;

	ShootingPeaVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ShootingPeaVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
