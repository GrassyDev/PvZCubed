package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum ExplorerVariants {
	EXPLORER(0),
	EXPLORERHYPNO(1),
	TORCHLIGHT(2),
	TORCHLIGHTHYPNO(3);

	private static final ExplorerVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ExplorerVariants::getId)).toArray(ExplorerVariants[]::new);
	private final int id;

	ExplorerVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ExplorerVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
