package io.github.GrassyDev.pvzmod.registry.entity.variants.challenge;

import java.util.Arrays;
import java.util.Comparator;

public enum TypeOfWorld {
	BASIC(0),
	NIGHT(1),
	POOL(2),
	ROOF(3),
	EGYPT(4),
	DARKAGES(5),
	FUTURE(6);

	private static final TypeOfWorld[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(TypeOfWorld::getId)).toArray(TypeOfWorld[]::new);
	private final int id;

	TypeOfWorld(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static TypeOfWorld byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
