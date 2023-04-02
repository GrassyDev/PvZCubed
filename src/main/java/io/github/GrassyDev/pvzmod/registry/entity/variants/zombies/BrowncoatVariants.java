package io.github.GrassyDev.pvzmod.registry.entity.variants.zombies;

import java.util.Arrays;
import java.util.Comparator;

public enum BrowncoatVariants {
	BROWNCOAT(0),
	BROWNCOATHYPNO(1),
	CONEHEAD(2),
	CONEHEADHYPNO(3),
	BUCKETHEAD(4),
	BUCKETHEADHYPNO(5),
	SCREENDOOR(6),
	SCREENDOORHYPNO(7),
	TRASHCAN(8),
	TRASHCANHYPNO(9),
	BRICKHEAD(10),
	BRICKHEADHYPNO(11),
	PEASANTKNIGHT(12),
	PEASANTKNIGHTHYPNO(13),
	PYRAMIDHEAD(14),
	PYRAMIDHEADHYPNO(15);

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
