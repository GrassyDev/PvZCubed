package io.github.GrassyDev.pvzmod;

import io.github.GrassyDev.pvzmod.config.PvZConfig;
import io.github.GrassyDev.pvzmod.registry.ModBlocks;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.HypnoDamage;
import io.github.GrassyDev.pvzmod.registry.entity.damage.LightningDamage;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.*;
import io.github.GrassyDev.pvzmod.registry.world.gen.entity.PvZEntitySpawn;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class PvZCubed implements ModInitializer {

	public static final PvZConfig PVZCONFIG = PvZConfig.createAndLoad();
	// This logger is used to write text to the console and the log file.
	public static final Logger LOGGER = LoggerFactory.getLogger("Plants vs. Zombies Cubed");

	// Thanks to Ennui Langeweile for the help with Registry Entry Attachments
	public static final RegistryEntryAttachment<EntityType<?>, String> ZOMBIE_MATERIAL =
			RegistryEntryAttachment.stringBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "zombie_material")).build();

	// Thanks to Ennui Langeweile for the help with Registry Entry Attachments
	public static final RegistryEntryAttachment<EntityType<?>, String> ZOMBIE_SIZE =
			RegistryEntryAttachment.stringBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "zombie_size")).build();

	public static final RegistryEntryAttachment<EntityType<?>, Boolean> IS_MACHINE =
			RegistryEntryAttachment.boolBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "is_machine")).build();
	public static final RegistryEntryAttachment<EntityType<?>, Integer> ZOMBIE_STRENGTH =
			RegistryEntryAttachment.intBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "zombie_strength")).build();
	public static final RegistryEntryAttachment<EntityType<?>, Boolean> TARGET_GROUND =
			RegistryEntryAttachment.boolBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "target_ground")).build();
	public static final RegistryEntryAttachment<EntityType<?>, Boolean> TARGET_FLY =
			RegistryEntryAttachment.boolBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "target_fly")).build();
	public static final RegistryEntryAttachment<EntityType<?>, String> PLANT_LOCATION =
			RegistryEntryAttachment.stringBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "plant_location")).build();
	public static final RegistryEntryAttachment<EntityType<?>, String> PLANT_TYPE =
			RegistryEntryAttachment.stringBuilder(Registry.ENTITY_TYPE, new Identifier("pvzmod", "plant_type")).build();

	public static final DamageSource HYPNO_DAMAGE = new HypnoDamage().setHypnoDamage();
	public static final DamageSource LIGHTNING_DAMAGE = new LightningDamage().setLightningDamage();

	public static final StatusEffect ICE = new Ice();
	public static final StatusEffect FROZEN = new Frozen();
	public static final StatusEffect STUN = new Stun();
	public static final StatusEffect BOUNCED = new Bounced();
	public static final StatusEffect DISABLE = new Disable();
	public static final StatusEffect WARM = new Warm();
	public static final StatusEffect WET = new Wet();
	public static final StatusEffect PVZPOISON = new PvZPoison();
	public static final StatusEffect ACID = new Acid();

	public static final GameRules.Key<GameRules.BooleanRule> SHOULD_PLANT_SPAWN =
			GameRuleRegistry.register("pvzdoPlantSpawn", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> SHOULD_GRAVE_SPAWN =
			GameRuleRegistry.register("pvzdoGraveSpawn", GameRules.Category.SPAWNING, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanRule> SHOULD_PLANT_DROP =
			GameRuleRegistry.register("pvzdoPlantDrop", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanRule> SHOULD_ZOMBIE_DROP =
			GameRuleRegistry.register("pvzdoZombieDrop", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(true));

	public static final GameRules.Key<GameRules.BooleanRule> INFINITE_SEEDS =
			GameRuleRegistry.register("pvzinfiniteSeeds", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(false));
	public static final GameRules.Key<GameRules.BooleanRule> INSTANT_RECHARGE =
			GameRuleRegistry.register("pvzinstantRecharge", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(false));

	public static final GameRules.Key<GameRules.BooleanRule> PLANTS_GLOW =
			GameRuleRegistry.register("pvzplantsGlow", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));

	public static final String MOD_ID = "pvzmod";

	public static final ItemGroup PVZPLANTS = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "plants"))
			.icon(() -> new ItemStack(ModItems.SUN))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.GARDEN_SPAWN));
				stacks.add(new ItemStack(ModItems.GARDENINGGLOVE));
				stacks.add(new ItemStack(ModItems.PLANTFOOD));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_AIR));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_AQUATIC));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_COLD));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_ELEC));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_FIRE));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_FLOWER));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_MUSHROOM));
				stacks.add(new ItemStack(ModItems.PLANTFOOD_TOUGH));
				stacks.add(new ItemStack(ModItems.SMALLSUN));
				stacks.add(new ItemStack(ModItems.SUN));
				stacks.add(new ItemStack(ModItems.LARGESUN));
				stacks.add(new ItemStack(ModItems.DAVES_SHOVEL));
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
				stacks.add(new ItemStack(ModItems.JALAPENO_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SPIKEWEED_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TORCHWOOD_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TALLNUT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SEASHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CABBAGEPULT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GATLINGPEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TWINSUNFLOWER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GLOOMSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CATTAIL_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SPIKEROCK_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.ICEBERGPULT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SHAMROCK_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CHILLYPEPPER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BEESHOOTER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SNOW_QUEENPEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BREEZESHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BLOOMERANG_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.ICEBERGLETTUCE_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SPRINGBEAN_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.COCONUTCANNON_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.LIGHTNINGREED_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEAPOD_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.EMPEACH_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEPPERPULT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.FIRE_PEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GOLDLEAF_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.MAGICSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.LOQUAT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SAUCER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TULIMPETER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.NARCISSUS_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.DROPEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BEAUTYSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CHARMSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.ACIDSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.DANDELIONWEED_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PERFOOMSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SMALLNUT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SMACKADAMIA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BUTTONSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BOMBSEEDLING_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.ZAPRICOT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.ADMIRALNAVYBEAN_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.WEENIEBEANIE_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.NAVYBEAN_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.JUMPINGBEAN_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SUNFLOWERSEED_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.BELLFLOWER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEA));
				stacks.add(new ItemStack(ModItems.SNOWPEAPROJ));
				stacks.add(new ItemStack(ModItems.SPORE));
				stacks.add(new ItemStack(ModItems.FUME));
				stacks.add(new ItemStack(ModItems.HYPNO));
				stacks.add(new ItemStack(ModItems.FIREPEA));
				stacks.add(new ItemStack(ModItems.ACIDSPORE));
				stacks.add(new ItemStack(ModItems.SPIKE));
				stacks.add(new ItemStack(ModItems.CABBAGE));
				stacks.add(new ItemStack(ModItems.ICEBERG));
				stacks.add(new ItemStack(ModItems.RAINBOWBULLET));
				stacks.add(new ItemStack(ModItems.BEESPIKE));
				stacks.add(new ItemStack(ModItems.POWERBEESPIKE));
				stacks.add(new ItemStack(ModItems.SNOWQUEENPEAPROJ));
				stacks.add(new ItemStack(ModItems.BREEZE));
				stacks.add(new ItemStack(ModItems.BOOMERANG));
				stacks.add(new ItemStack(ModItems.COCONUT));
				stacks.add(new ItemStack(ModItems.PEPPER));
				stacks.add(new ItemStack(ModItems.PLASMAPEA));
				stacks.add(new ItemStack(ModItems.POWERSPIKE));
				stacks.add(new ItemStack(ModItems.ICESPIKE));
				stacks.add(new ItemStack(ModItems.POWERICESPIKE));
				stacks.add(new ItemStack(ModItems.CARDPROJ));
				stacks.add(new ItemStack(ModItems.HYPNOPROJ));
				stacks.add(new ItemStack(ModItems.BUBBLES));
				stacks.add(new ItemStack(ModItems.ARMORBUBBLE));
				stacks.add(new ItemStack(ModItems.DROP));
				stacks.add(new ItemStack(ModItems.ACIDFUME));
				stacks.add(new ItemStack(ModItems.SPIT));
				stacks.add(new ItemStack(ModItems.JINGLE));
			}).build();

	public static final ItemGroup PVZZOMBIES = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "zombies"))
			.icon(() -> new ItemStack(ModItems.BRAIN))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.BRAIN));
				stacks.add(new ItemStack(ModItems.LOCUSTSWARMEGG));
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
				stacks.add(new ItemStack(ModItems.DOLPHINRIDEREGG));
				stacks.add(new ItemStack(ModItems.GARGANTUAREGG));
				stacks.add(new ItemStack(ModItems.IMPEGG));
				stacks.add(new ItemStack(ModItems.TRASHCANEGG));
				stacks.add(new ItemStack(ModItems.BERSERKEREGG));
				stacks.add(new ItemStack(ModItems.MUMMYEGG));
				stacks.add(new ItemStack(ModItems.FLAGMUMMYEGG));
				stacks.add(new ItemStack(ModItems.MUMMYCONEEGG));
				stacks.add(new ItemStack(ModItems.MUMMYBUCKETEGG));
				stacks.add(new ItemStack(ModItems.EXPLOREREGG));
				stacks.add(new ItemStack(ModItems.UNDYINGEGG));
				stacks.add(new ItemStack(ModItems.PHARAOHEGG));
				stacks.add(new ItemStack(ModItems.TORCHLIGHTEGG));
				stacks.add(new ItemStack(ModItems.PYRAMIDHEADEGG));
				stacks.add(new ItemStack(ModItems.JETPACKEGG));
				stacks.add(new ItemStack(ModItems.ROBOCONEEGG));
				stacks.add(new ItemStack(ModItems.BLASTRONAUTEGG));
				stacks.add(new ItemStack(ModItems.PEASANTEGG));
				stacks.add(new ItemStack(ModItems.FLAGPEASANTEGG));
				stacks.add(new ItemStack(ModItems.PEASANTCONEEGG));
				stacks.add(new ItemStack(ModItems.PEASANTBUCKETEGG));
				stacks.add(new ItemStack(ModItems.PEASANTKNIGHTEGG));
				stacks.add(new ItemStack(ModItems.ANNOUNCERIMPEGG));
				stacks.add(new ItemStack(ModItems.ZOMBIEKINGEGG));
				stacks.add(new ItemStack(ModItems.IMPDRAGONEGG));
				stacks.add(new ItemStack(ModItems.BRICKHEADEGG));
				stacks.add(new ItemStack(ModItems.SUNDAYEDITIONEGG));
				stacks.add(new ItemStack(ModItems.SUPERFANIMPEGG));
				stacks.add(new ItemStack(ModItems.NEWYEARIMPEGG));
				stacks.add(new ItemStack(ModItems.DEFENSIVEENDEGG));
				stacks.add(new ItemStack(ModItems.BULLYEGG));
				stacks.add(new ItemStack(ModItems.BASKETBALLCARRIEREGG));
			}).build();

	public static final ItemGroup PVZGRAVES = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "graves"))
			.icon(() -> new ItemStack(ModItems.ZOMBIEGRAVESPAWN))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.ZOMBIEGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.EASY));
				stacks.add(new ItemStack(ModItems.EASYMED));
				stacks.add(new ItemStack(ModItems.MED));
				stacks.add(new ItemStack(ModItems.MEDHARD));
				stacks.add(new ItemStack(ModItems.HARD));
				stacks.add(new ItemStack(ModItems.SUPERHARD));
				stacks.add(new ItemStack(ModItems.NIGHTMARE));
				stacks.add(new ItemStack(ModItems.CRAAAAZY));
				stacks.add(new ItemStack(ModItems.INFINITE));
				stacks.add(new ItemStack(ModItems.UNLOCK));
				stacks.add(new ItemStack(ModItems.ONEBYONE));
				stacks.add(new ItemStack(ModItems.HALF));
				stacks.add(new ItemStack(ModItems.BASICGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.NIGHTGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.POOLGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.ROOFGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.EGYPTGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.FUTUREGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.DARKAGESGRAVESPAWN));
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
				stacks.add(new ItemStack(ModItems.EGYPT_TILE));
				stacks.add(new ItemStack(ModItems.DARK_EGYPT_TILE));
				stacks.add(new ItemStack(ModItems.PIRATE_TILE));
				stacks.add(new ItemStack(ModItems.DARK_PIRATE_TILE));
				stacks.add(new ItemStack(ModItems.WEST_TILE));
				stacks.add(new ItemStack(ModItems.DARK_WEST_TILE));
				stacks.add(new ItemStack(ModItems.FUTURE_TILE));
				stacks.add(new ItemStack(ModItems.DARK_FUTURE_TILE));
				stacks.add(new ItemStack(ModItems.NIGHT_TILE));
				stacks.add(new ItemStack(ModItems.DARK_NIGHT_TILE));
				stacks.add(new ItemStack(ModItems.SAND_TILE));
				stacks.add(new ItemStack(ModItems.DARK_SAND_TILE));
				stacks.add(new ItemStack(ModItems.UNDERWATER_TILE));
				stacks.add(new ItemStack(ModItems.DARK_UNDERWATER_TILE));
				stacks.add(new ItemStack(ModItems.FROST_TILE));
				stacks.add(new ItemStack(ModItems.DARK_FROST_TILE));
				stacks.add(new ItemStack(ModItems.LOSTCITY_TILE));
				stacks.add(new ItemStack(ModItems.DARK_LOSTCITY_TILE));
				stacks.add(new ItemStack(ModItems.SKYCITY_TILE));
				stacks.add(new ItemStack(ModItems.DARK_SKYCITY_TILE));
			}).build();

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("{} says: Trans Rights are Human Rights!", mod.metadata().name());

		ModItems.registerItems();
		ModItems.setSeedPacketList();
		ModBlocks.registerBlocks();
		GeckoLib.initialize();
		PvZEntitySpawn.addEntitySpawn();
		PvZSounds.registerSounds();
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "ice"), ICE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "frozen"), FROZEN);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "stun"), STUN);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "bounced"), BOUNCED);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "disable"), DISABLE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "warm"), WARM);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "wet"), WET);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "pvzpoison"), PVZPOISON);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "acid"), ACID);
	}
}
