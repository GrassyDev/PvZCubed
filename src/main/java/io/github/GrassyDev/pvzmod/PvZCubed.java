package io.github.GrassyDev.pvzmod;

import io.github.GrassyDev.pvzmod.registry.ModBlocks;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.damage.HypnoDamage;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Frozen;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Hypnotized;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Ice;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Warm;
import io.github.GrassyDev.pvzmod.registry.world.gen.entity.PvZEntitySpawn;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class PvZCubed implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Plants vs. Zombies Cubed");

	public static final DamageSource HYPNO_DAMAGE = new HypnoDamage().setHypnoDamage();
	public static final StatusEffect HYPNOTIZED = new Hypnotized();

	public static final StatusEffect ICE = new Ice();
	public static final StatusEffect FROZEN = new Frozen();
	public static final StatusEffect WARM = new Warm();

	public static final String MOD_ID = "pvzmod";

	public static final ItemGroup PLANTS = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "plants"))
			.icon(() -> new ItemStack(ModItems.SUN))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.PLANTFOOD));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_AQUATIC));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_COLD));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_FIRE));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_MUSHROOM));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_TOUGH));
				stacks.add(new ItemStack(ModItems.DAVES_SHOVEL));
				stacks.add(new ItemStack(ModItems.SMALLSUN));
				stacks.add(new ItemStack(ModItems.SUN));
				stacks.add(new ItemStack(ModItems.LARGESUN));
				stacks.add(new ItemStack(ModItems.PEASHOOTER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SUNFLOWER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CHERRYBOMB_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.WALLNUT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.POTATOMINE_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SNOW_PEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CHOMPER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.REPEATER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PUFFSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SUNSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.FUMESHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GRAVEBUSTER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.HYPNOSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SCAREDYSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.ICESHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.DOOMSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.LILYPAD_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SQUASH_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.THREEPEATER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TANGLEKELP_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CABBAGEPULT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GATLINGPEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TWINSUNFLOWER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SNOW_QUEENPEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PERFOOMSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEAPOD_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.FIRE_PEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SMALLNUT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BUTTONSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BOMBSEEDLING_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEA));
				stacks.add(new ItemStack(ModItems.SNOWPEAPROJ));
				stacks.add(new ItemStack(ModItems.FUME));
				stacks.add(new ItemStack(ModItems.SPORE));
				stacks.add(new ItemStack(ModItems.FIREPEA));
				stacks.add(new ItemStack(ModItems.SNOWQUEENPEAPROJ));
				stacks.add(new ItemStack(ModItems.PLASMAPEA));
				stacks.add(new ItemStack(ModItems.ICESPIKE));
			}).build();

	public static final ItemGroup ZOMBIES = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "zombies"))
			.icon(() -> new ItemStack(ModItems.WAVE_FLAG))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.BROWNCOATEGG));
				stacks.add(new ItemStack(ModItems.FLAGZOMBIEEGG));
				stacks.add(new ItemStack(ModItems.CONEHEADEGG));
				stacks.add(new ItemStack(ModItems.POLEVAULTINGEGG));
				stacks.add(new ItemStack(ModItems.BUCKETHEADEGG));
				stacks.add(new ItemStack(ModItems.NEWSPAPEREGG));
				stacks.add(new ItemStack(ModItems.SCREENDOOREGG));
				stacks.add(new ItemStack(ModItems.FOOTBALLEGG));
				stacks.add(new ItemStack(ModItems.DANCINGZOMBIEEGG));
				stacks.add(new ItemStack(ModItems.BACKUPDANCEREGG));
				stacks.add(new ItemStack(ModItems.SNORKELEGG));
				stacks.add(new ItemStack(ModItems.GARGANTUAREGG));
				stacks.add(new ItemStack(ModItems.IMPEGG));
				stacks.add(new ItemStack(ModItems.BERSERKEREGG));
			}).build();

	public static final ItemGroup GRAVES = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "graves"))
			.icon(() -> new ItemStack(ModItems.BASICGRAVESPAWN))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.BASICGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.NIGHTGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.POOLGRAVESPAWN));
			}).build();

	public static final ItemGroup PVZBLOCKS = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "blocks"))
			.icon(() -> new ItemStack(ModItems.PREMIUM_TILE))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.GRASS_TILE));
				stacks.add(new ItemStack(ModItems.DARK_GRASS_TILE));
				stacks.add(new ItemStack(ModItems.ROOF_TILE));
				stacks.add(new ItemStack(ModItems.DARK_ROOF_TILE));
				stacks.add(new ItemStack(ModItems.UPGRADE_TILE));
				stacks.add(new ItemStack(ModItems.PREMIUM_TILE));
				stacks.add(new ItemStack(ModItems.PIRATE_TILE));
				stacks.add(new ItemStack(ModItems.DARK_PIRATE_TILE));
				stacks.add(new ItemStack(ModItems.WEST_TILE));
				stacks.add(new ItemStack(ModItems.DARK_WEST_TILE));
				stacks.add(new ItemStack(ModItems.NIGHT_TILE));
				stacks.add(new ItemStack(ModItems.DARK_NIGHT_TILE));
				stacks.add(new ItemStack(ModItems.SAND_TILE));
				stacks.add(new ItemStack(ModItems.DARK_SAND_TILE));
			}).build();

	public static final Identifier PEASHOOT = new Identifier("pvzmod:pea.shoot");
	public static SoundEvent PEASHOOTEVENT = new SoundEvent(PEASHOOT);
	public static final Identifier PEAHIT = new Identifier("pvzmod:pea.hit");
	public static SoundEvent PEAHITEVENT = new SoundEvent(PEAHIT);
	public static final Identifier GATLINGPEAHIT = new Identifier("pvzmod:gatlingpea.hit");
	public static SoundEvent GATLINGPEAHITEVENT = new SoundEvent(GATLINGPEAHIT);
	public static final Identifier GATLINGPEASHOOT = new Identifier("pvzmod:gatlingpea.shoot");
	public static SoundEvent GATLINGPEASHOOTEVENT = new SoundEvent(GATLINGPEASHOOT);

	public static final Identifier FIREPEAHIT = new Identifier("pvzmod:flaming.pea.hit");
	public static SoundEvent FIREPEAHITEVENT = new SoundEvent(FIREPEAHIT);

	public static final Identifier CONEHIT = new Identifier("pvzmod:cone.hit");
	public static SoundEvent CONEHITEVENT = new SoundEvent(CONEHIT);

	public static final Identifier BUCKETHIT = new Identifier("pvzmod:bucket.hit");
	public static SoundEvent BUCKETHITEVENT = new SoundEvent(BUCKETHIT);

	public static final Identifier SNOWPEASHOOT = new Identifier("pvzmod:snowpea.shoot");
	public static SoundEvent SNOWPEASHOOTEVENT = new SoundEvent(SNOWPEASHOOT);
	public static final Identifier SNOWPEAHIT = new Identifier("pvzmod:snowpea.hit");
	public static SoundEvent SNOWPEAHITEVENT = new SoundEvent(SNOWPEAHIT);

	public static final Identifier MUSHROOMSHOOT = new Identifier("pvzmod:mushroom.shoot");
	public static SoundEvent MUSHROOMSHOOTEVENT = new SoundEvent(MUSHROOMSHOOT);

	public static final Identifier FUMESHROOMSHOOT = new Identifier("pvzmod:fumeshroom.shoot");
	public static SoundEvent FUMESHROOMSHOOTEVENT = new SoundEvent(FUMESHROOMSHOOT);

	public static final Identifier CHERRYBOMBEXPLOSION = new Identifier("pvzmod:cherrybomb.explosion");
	public static SoundEvent CHERRYBOMBEXPLOSIONEVENT = new SoundEvent(CHERRYBOMBEXPLOSION);
	public static final Identifier POTATOMINEEXPLOSION = new Identifier("pvzmod:potatomine.explosion");
	public static SoundEvent POTATOMINEEXPLOSIONEVENT = new SoundEvent(POTATOMINEEXPLOSION);
	public static final Identifier DOOMSHROOMEXPLOSION = new Identifier("pvzmod:doomshroom.explosion");
	public static SoundEvent DOOMSHROOMEXPLOSIONEVENT = new SoundEvent(DOOMSHROOMEXPLOSION);

	public static final Identifier SQUASHHUM = new Identifier("pvzmod:squash.hm");
	public static SoundEvent SQUASHHUMEVENT = new SoundEvent(SQUASHHUM);

	public static final Identifier CHOMPERBITE = new Identifier("pvzmod:chomper.chomp");
	public static SoundEvent CHOMPERBITEVENT = new SoundEvent(CHOMPERBITE);

	public static final Identifier GRAVEBUSTEREATING = new Identifier("pvzmod:gravebuster.eating");
	public static SoundEvent GRAVEBUSTEREATINGEVENT = new SoundEvent(GRAVEBUSTEREATING);

	public static final Identifier HYPNOTIZING = new Identifier("pvzmod:hypnoshroom.hypnotizing");
	public static SoundEvent HYPNOTIZINGEVENT = new SoundEvent(HYPNOTIZING);

	public static final Identifier GRAVERISING = new Identifier("pvzmod:grave.rising");
	public static SoundEvent GRAVERISINGEVENT = new SoundEvent(GRAVERISING);

	public static final Identifier ENTITYRISING = new Identifier("pvzmod:entity.rising");
	public static SoundEvent ENTITYRISINGEVENT = new SoundEvent(ENTITYRISING);

	public static final Identifier PLANTPLANTED = new Identifier("pvzmod:plant.planted");
	public static SoundEvent PLANTPLANTEDEVENT = new SoundEvent(PLANTPLANTED);

	public static final Identifier SUNDROP = new Identifier("pvzmod:sun.drop");
	public static SoundEvent SUNDROPEVENT = new SoundEvent(SUNDROP);

	public static final Identifier ZOMBIEBITE = new Identifier("pvzmod:zombie.bite");
	public static SoundEvent ZOMBIEBITEEVENT = new SoundEvent(ZOMBIEBITE);

	public static final Identifier POLEVAULT = new Identifier("pvzmod:polevaulting.vault");
	public static SoundEvent POLEVAULTEVENT = new SoundEvent(POLEVAULT);

	public static final Identifier GARGANTUARSMASH = new Identifier("pvzmod:gargantuar.smash");
	public static SoundEvent GARGANTUARSMASHEVENT = new SoundEvent(GARGANTUARSMASH);

	public static final Identifier IMPLAUNCH = new Identifier("pvzmod:imp.launch");
	public static SoundEvent IMPLAUNCHEVENT = new SoundEvent(IMPLAUNCH);

	public static final Identifier ZOMBIEMOAN = new Identifier("pvzmod:zombie.moan");
	public static SoundEvent ZOMBIEMOANEVENT = new SoundEvent(ZOMBIEMOAN);

	public static final Identifier GARGANTUARMOAN = new Identifier("pvzmod:gargantuar.moan");
	public static SoundEvent GARGANTUARMOANEVENT = new SoundEvent(GARGANTUARMOAN);

	public static final Identifier IMPMOAN = new Identifier("pvzmod:imp.moan");
	public static SoundEvent IMPMOANEVENT = new SoundEvent(IMPMOAN);

	public static final Identifier NEWSPAPERANGRY = new Identifier("pvzmod:newspaper.angry");
	public static SoundEvent NEWSPAPERANGRYEVENT = new SoundEvent(NEWSPAPERANGRY);

	public static final Identifier ZOMBIEDANCING = new Identifier("pvzmod:zombie.dancing");
	public static SoundEvent ZOMBIEDANCINGEVENT = new SoundEvent(ZOMBIEDANCING);

	public static final Identifier SILENCE = new Identifier("pvzmod:silence");
	public static SoundEvent SILENCEVENET = new SoundEvent(SILENCE);


	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("{} says: Trans Rights are Human Rights!", mod.metadata().name());
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		GeckoLib.initialize();
		PvZEntitySpawn.addEntitySpawn();
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "ice"), ICE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "frozen"), FROZEN);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "hypnotized"), HYPNOTIZED);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "warm"), WARM);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEASHOOT, PEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEAHIT, PEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FIREPEAHIT, FIREPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEASHOOT, GATLINGPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEAHIT, GATLINGPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CONEHIT, CONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.BUCKETHIT, BUCKETHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEASHOOT, SNOWPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEAHIT, SNOWPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.MUSHROOMSHOOT, MUSHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FUMESHROOMSHOOT, FUMESHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHERRYBOMBEXPLOSION, CHERRYBOMBEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POTATOMINEEXPLOSION, POTATOMINEEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.DOOMSHROOMEXPLOSION, DOOMSHROOMEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SQUASHHUM, SQUASHHUMEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHOMPERBITE, CHOMPERBITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVEBUSTEREATING, GRAVEBUSTEREATINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.HYPNOTIZING, HYPNOTIZINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVERISING, GRAVERISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ENTITYRISING, ENTITYRISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PLANTPLANTED, PLANTPLANTEDEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SUNDROP, SUNDROPEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEBITE, ZOMBIEBITEEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POLEVAULT, POLEVAULTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GARGANTUARSMASH, GARGANTUARSMASHEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.IMPLAUNCH, IMPLAUNCHEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEMOAN, ZOMBIEMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GARGANTUARMOAN, GARGANTUARMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SILENCE, SILENCEVENET);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEDANCING, ZOMBIEDANCINGEVENT);
	}

	private static boolean IsZero(double d) {
        double eps = 1e-9;
		return d > -eps && d < eps;
	}

	private static double GetCubicRoot(double value)
	{
		if (value > 0.0) {
			return Math.pow(value, 1D / 3D);
		} else if (value < 0) {
			return Math.pow(-value, 1.0 / 3.0);
		} else {
			return 0.0;
		}
	}

	// Solve quadratic equation: c0*x^2 + c1*x + c2.
	// Returns number of solutions.
	public static double[] SolveQuadric(double c0, double c1, double c2) {

		double[] returnVal = new double[2];
		returnVal[0] = Double.NaN;
		returnVal[1] = Double.NaN;

		double p, q, D;

		/* normal form: x^2 + px + q = 0 */
		p = c1 / (2 * c0);
		q = c2 / c0;

		D = p * p - q;

		if (IsZero(D)) {
			returnVal[0] = -p;
			return returnVal;
		}
		else if (D < 0) {
			return returnVal;
		}
		else /* if (D > 0) */ {
			double sqrt_D = Math.sqrt(D);

			returnVal[0] =   sqrt_D - p;
			returnVal[1] = -sqrt_D - p;
			return returnVal;
		}
	}

	// Solve cubic equation: c0*x^3 + c1*x^2 + c2*x + c3.
	// Returns number of solutions.
	public static double[] SolveCubic(double c0, double c1, double c2, double c3)
	{
		double[] returnVal = new double[3];
		returnVal[0] = Double.NaN;
		returnVal[1] = Double.NaN;
		returnVal[2] = Double.NaN;

		int     num;
		double  sub;
		double  A, B, C;
		double  sq_A, p, q;
		double  cb_p, D;

		/* normal form: x^3 + Ax^2 + Bx + C = 0 */
		A = c1 / c0;
		B = c2 / c0;
		C = c3 / c0;

		/*  substitute x = y - A/3 to eliminate quadric term:  x^3 +px + q = 0 */
		sq_A = A * A;
		p = 1.0/3 * (- 1.0/3 * sq_A + B);
		q = 1.0/2 * (2.0/27 * A * sq_A - 1.0/3 * A * B + C);

		/* use Cardano's formula */
		cb_p = p * p * p;
		D = q * q + cb_p;

		if (IsZero(D)) {
			if (IsZero(q)) /* one triple solution */ {
				returnVal[0] = 0;
				num = 1;
			}
			else /* one single and one double solution */ {
				double u = GetCubicRoot(-q);
				returnVal[0] = 2 * u;
				returnVal[1] = - u;
				num = 2;
			}
		}
		else if (D < 0) /* Casus irreducibilis: three real solutions */ {
			double phi = 1.0/3 * Math.acos(-q / Math.sqrt(-cb_p));
			double t = 2 * Math.sqrt(-p);

			returnVal[0] =   t * Math.cos(phi);
			returnVal[1] = - t * Math.cos(phi + Math.PI / 3);
			returnVal[2] = - t * Math.cos(phi - Math.PI / 3);
			num = 3;
		}
		else /* one real solution */ {
			double sqrt_D = Math.sqrt(D);
			double u = GetCubicRoot(sqrt_D - q);
			double v = -GetCubicRoot(sqrt_D + q);

			returnVal[0] = u + v;
			num = 1;
		}

		/* resubstitute */
		sub = 1.0/3 * A;

		if (num > 0)    returnVal[0] -= sub;
		if (num > 1)    returnVal[1] -= sub;
		if (num > 2)    returnVal[2] -= sub;

		return returnVal;
	}

	// Solve quartic function: c0*x^4 + c1*x^3 + c2*x^2 + c3*x + c4.
	// Returns number of solutions.
	public static double[] SolveQuartic(double c0, double c1, double c2, double c3, double c4) {

		double[] returnValue = new double[4];
		returnValue[0] = Double.NaN;
		returnValue[1] = Double.NaN;
		returnValue[2] = Double.NaN;
		returnValue[3] = Double.NaN;

		double[] coeffs = new double[4];
		double[] z;
		double u;
		double v;
		double sub;
		double  A, B, C, D;
		double  sq_A, p, q, r;
		double[] num;

		/* normal form: x^4 + Ax^3 + Bx^2 + Cx + D = 0 */
		A = c1 / c0;
		B = c2 / c0;
		C = c3 / c0;
		D = c4 / c0;

		/*  substitute x = y - A/4 to eliminate cubic term: x^4 + px^2 + qx + r = 0 */
		sq_A = A * A;
		p = - 3.0/8 * sq_A + B;
		q = 1.0/8 * sq_A * A - 1.0/2 * A * B + C;
		r = - 3.0/256*sq_A*sq_A + 1.0/16*sq_A*B - 1.0/4*A*C + D;

		if (IsZero(r)) {
			/* no absolute term: y(y^3 + py + q) = 0 */

			coeffs[ 3 ] = q;
			coeffs[ 2 ] = p;
			coeffs[ 1 ] = 0;
			coeffs[ 0 ] = 1;

			num = SolveCubic(coeffs[0], coeffs[1], coeffs[2], coeffs[3]);
		}
		else {
			/* solve the resolvent cubic ... */
			coeffs[ 3 ] = 1.0/2 * r * p - 1.0/8 * q * q;
			coeffs[ 2 ] = - r;
			coeffs[ 1 ] = - 1.0/2 * p;
			coeffs[ 0 ] = 1;

			SolveCubic(coeffs[0], coeffs[1], coeffs[2], coeffs[3]);

			/* ... and take the one real solution ... */
			z = returnValue;

			/* ... to build two quadric equations */
			u = z[0] * z[0] - r;
			v = 2 * z[0] - p;

			if (IsZero(u))
				u = 0;
			else if (u > 0)
				u = Math.sqrt(u);
			else
				return returnValue;

			if (IsZero(v))
				v = 0;
			else if (v > 0)
				v = Math.sqrt(v);
			else
				return returnValue;

			coeffs[ 2 ] = z[0] - u;
			coeffs[ 1 ] = q < 0 ? -v : v;
			coeffs[ 0 ] = 1;

			num = SolveQuadric(coeffs[0], coeffs[1], coeffs[2]);

			coeffs[ 2 ]= z[0] + u;
			coeffs[ 1 ] = q < 0 ? v : -v;
			coeffs[ 0 ] = 1;

			double[] num2 = SolveQuadric(coeffs[0], coeffs[1], coeffs[2]);
			num[0] += num2[0];
			num[1] += num2[1];
			num[2] += num2[2];
			num[3] += num2[3];
		}

		/* resubstitute */
		sub = 1.0/4 * A;

		if (num[0] > 0)    returnValue[0] -= sub;
		if (num[1] > 1)    returnValue[1] -= sub;
		if (num[2] > 2)    returnValue[2] -= sub;
		if (num[3] > 3)    returnValue[3] -= sub;

		return num;
	}
}
