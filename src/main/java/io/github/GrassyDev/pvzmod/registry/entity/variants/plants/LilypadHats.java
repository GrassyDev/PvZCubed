package io.github.GrassyDev.pvzmod.registry.entity.variants.plants;

import java.util.Arrays;
import java.util.Comparator;

public enum LilypadHats {
	DEFAULT(0),
	LILY(1);

	private static final LilypadHats[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(LilypadHats::getId)).toArray(LilypadHats[]::new);
	private final int id;

	LilypadHats(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static LilypadHats byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
