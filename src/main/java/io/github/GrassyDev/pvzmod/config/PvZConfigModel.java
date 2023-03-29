package io.github.GrassyDev.pvzmod.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "pvzmod")
@Config(name = "pvz-config", wrapperName = "PvZConfig")
public class PvZConfigModel {
	@Sync(Option.SyncMode.OVERRIDE_CLIENT)

	@SectionHeader("plants")
	@Nest
	public PvZSeedNest nestedSeeds = new PvZSeedNest();
	public static class PvZSeedNest {
		public boolean infiniteSeeds = false;
		public boolean instantRecharge = false;
		public float acidshrooomS = 15.0f;
		public float admiralnavybeanS = 10.0f;
		public float beeshooterS = 15.0f;
		public float bellflowerS = 2.5f;
		public float bombseedlingS = 2.5f;
		public float buttonshroomS = 2.5f;
		public float cabbagepultS = 5.0f;
		public float cattailS = 35.0f;
		public float cherrybombS = 35.0f;
		public float chomperS = 20.0f;
		public float coconutS = 35.0f;
		public float dandelionweedS = 7.5f;
		public float doomshroomS = 25.0f;
		public float firepeaS = 15.0f;
		public float fumeshroomS = 7.5f;
		public float gatlingpeaS = 35.0f;
		public float gloomshroomS = 35.0f;
		public float gravebusterS = 5.0f;
		public float hypnoshroomS = 20.0f;
		public float iceberglettuceS = 20.0f;
		public float iceshroomS = 25.0f;
		public float jalapenoS = 35.0f;
		public float lightningreedS = 12.5f;
		public float lilypadS = 0f;
		public float loquatS = 2.5f;
		public float narcissusS = 7.5f;
		public float navybeanS = 5.0f;
		public float peapodS = 3.75f;
		public float peashooterS = 5.0f;
		public float pepperpultS = 20.0f;
		public float perfoomshroomS = 25.0f;
		public float potatomineS = 20.0f;
		public float puffshroomS = 2.5f;
		public float repeaterS = 7.5f;
		public float scaredyshroomS = 5.0f;
		public float seashroomS = 10.0f;
		public float shamrockS = 5.0f;
		public float smackadamiaS = 50.0f;
		public float smallnutS = 10.0f;
		public float snowpeaS = 7.5f;
		public float snowqueenpeaS = 35f;
		public float spikerockS = 35.0f;
		public float spikeweedS = 5.0f;
		public float squashS = 25.0f;
		public float sunflowerS = 2.5f;
		public float sunflowerseedS = 2.5f;
		public float sunshroomS = 5.0f;
		public float tallnutS = 50.0f;
		public float tanglekelpS = 25.0f;
		public float threepeaterS = 7.5f;
		public float torchwoodS = 37.5f;
		public float twinsunflowerS = 35.0f;
		public float wallnutS = 50.0f;
		public float weeniebeanieS = 2.5f;
		public float zapricotS = 7.5f;
	}
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
