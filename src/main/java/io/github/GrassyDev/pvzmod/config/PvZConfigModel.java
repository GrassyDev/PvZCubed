package io.github.GrassyDev.pvzmod.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "pvzmod")
@Config(name = "pvz-config", wrapperName = "PvZConfig")
public class PvZConfigModel {
	@Sync(Option.SyncMode.OVERRIDE_CLIENT)

	@SectionHeader("sunProduction")
	@Nest
	public PvZSunNest nestedSun = new PvZSunNest();
	public static class PvZSunNest {
		public float sunflowerSec = 120f;
		public float sunflowerSecInitial = 120f;
		public boolean sunflowerDropSun = true;
		public float twinSunflowerSec = 120f;
		public float sunshroomSec = 120f;
		public float sunshroomSecInitial = 5f;
		public float sunshroomSunChance = 0.45f;
		public float sunshroomSun2ndChance = 0.75f;
	}

	@SectionHeader("projDMG")
	@Nest
	public PvZDMGNest nestedProjDMG = new PvZDMGNest();
	public static class PvZDMGNest {
		public float acidFumeDMG = 2.5f;
		public float acidSporeDMG = 3f;
		public float armorBubbleDMG = 2f;
		public float beeSpikeDMG = 2f;
		public float bubblesDMG = 2f;
		public float cabbageDMG = 4f;
		public float coconutDMG = 90f;
		public float flamingPeaDMG = 4f;
		public float fumeDMG = 2.5f;
		public float iceSpikeDMG = 2f;
		public float iceSpikeMultiplier = 2f;
		public float jingleDMG = 8f;
		public float peaDMG = 4f;
		public float pepperDMG = 3f;
		public float plasmaPeaDMG = 12f;
		public float rainbowBulletDMG = 4f;
		public float snowPeaDMG = 4f;
		public float snowQueenPeaDMG = 4f;
		public float spikeDMG = 2f;
		public float spitDMG = 2f;
		public float sporeDMG = 2f;

		public float basketBallDMG = 4f;
	}

	@SectionHeader("zombieVar")
	@Nest
	public PvZZombieHealth nestedZombieHealth = new PvZZombieHealth();
	public static class PvZZombieHealth {
		public double basicGraveH = 140D;
		public double nightGraveH = 170D;
		public double poolGraveH = 200D;
		public double roofGraveH = 230D;
		public double futureGraveH = 240D;
		public double darkAgesGraveH = 170D;

		public double backupH = 27D;
		public double browncoatH = 27D;
		public double peasantH = 27D;
		public double bullyH = 110D;
		public double basketballH = 110D;
		public double dancingH = 50D;
		public double dolphinH = 50D;
		public double flagH = 50D;
		public double flagPeasantH = 50D;
		public double footballH = 27D;
		public double berserkerH = 27D;
		public double gargantuarH = 360D;
		public double defensiveendH = 360D;
		public double impH = 20D;
		public double superFanH = 20D;
		public double announcerH = 37D;
		public double jetpackH = 50D;
		public double blastronautH = 50D;
		public double newspaperH = 27D;
		public double sundayH = 50D;
		public double poleH = 50D;
		public double snorkelH = 50D;
		public double zombiekingH = 100D;

		public double coneH = 37D;

		public double bucketH = 110D;
		public double medallionH = 37D;
		public double footballHelmH = 140D;
		public double berserkerHelmH = 333D;
		public double defensiveendHelmH = 140D;
		public double blastronautHelmH = 37D;
		public double knightHelmH = 152D;

		public double coneTowerH = 53D;
		public double brickH = 212D;

		public double screendoorShieldH = 153D;

		public double newspaperShieldH = 15D;
		public double sundayShieldH = 115D;

		public double trashcanObstH = 225D;
		public double basketballObstH = 120D;
	}
}
