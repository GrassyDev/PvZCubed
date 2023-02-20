package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum BackupDancerVariants {
	BACKUPDANCER(0),
	BACKUPDANCERHYPNO(1);

	private static final BackupDancerVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(BackupDancerVariants::getId)).toArray(BackupDancerVariants[]::new);
	private final int id;

	BackupDancerVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static BackupDancerVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
