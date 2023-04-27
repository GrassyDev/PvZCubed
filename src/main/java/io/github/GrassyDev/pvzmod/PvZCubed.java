package io.github.GrassyDev.pvzmod;

import io.github.GrassyDev.pvzmod.config.PvZConfig;
import io.github.GrassyDev.pvzmod.registry.ModBlocks;
import io.github.GrassyDev.pvzmod.registry.ModItems;
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
import net.minecraft.sound.SoundEvent;
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
				stacks.add(new ItemStack(ModItems.ICEBERGLETTUCE_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.COCONUTCANNON_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.LIGHTNINGREED_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEAPOD_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.EMPEACH_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEPPERPULT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.FIRE_PEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GOLDLEAF_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.LOQUAT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SAUCER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.NARCISSUS_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.DROPEA_SEED_PACKET));
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
				stacks.add(new ItemStack(ModItems.SNOWQUEENPEAPROJ));
				stacks.add(new ItemStack(ModItems.COCONUT));
				stacks.add(new ItemStack(ModItems.PEPPER));
				stacks.add(new ItemStack(ModItems.PLASMAPEA));
				stacks.add(new ItemStack(ModItems.ICESPIKE));
				stacks.add(new ItemStack(ModItems.BUBBLES));
				stacks.add(new ItemStack(ModItems.ARMORBUBBLE));
				stacks.add(new ItemStack(ModItems.DROP));
				stacks.add(new ItemStack(ModItems.ACIDFUME));
				stacks.add(new ItemStack(ModItems.SPIT));
				stacks.add(new ItemStack(ModItems.JINGLE));
			}).build();

	public static final ItemGroup PVZZOMBIES = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "zombies"))
			.icon(() -> new ItemStack(ModItems.WAVE_FLAG))
			.appendItems(stacks -> {
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
			.icon(() -> new ItemStack(ModItems.BASICGRAVESPAWN))
			.appendItems(stacks -> {
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

	public static final Identifier LOOTNUGGET = new Identifier("pvzmod:loot.nugget");
	public static SoundEvent LOOTNUGGETEVENT = new SoundEvent(LOOTNUGGET);
	public static final Identifier LOOTDIAMOND = new Identifier("pvzmod:loot.diamond");
	public static SoundEvent LOOTDIAMONDEVENT = new SoundEvent(LOOTDIAMOND);
	public static final Identifier LOOTGIFT = new Identifier("pvzmod:loot.gift");
	public static SoundEvent LOOTGIFTDEVENT = new SoundEvent(LOOTGIFT);

	public static final Identifier PEASHOOT = new Identifier("pvzmod:pea.shoot");
	public static SoundEvent PEASHOOTEVENT = new SoundEvent(PEASHOOT);
	public static final Identifier PEAHIT = new Identifier("pvzmod:pea.hit");
	public static SoundEvent PEAHITEVENT = new SoundEvent(PEAHIT);

	public static final Identifier FIREPEAHIT = new Identifier("pvzmod:flaming.pea.hit");
	public static SoundEvent FIREPEAHITEVENT = new SoundEvent(FIREPEAHIT);

	public static final Identifier CONEHIT = new Identifier("pvzmod:cone.hit");
	public static SoundEvent CONEHITEVENT = new SoundEvent(CONEHIT);

	public static final Identifier BUCKETHIT = new Identifier("pvzmod:bucket.hit");
	public static SoundEvent BUCKETHITEVENT = new SoundEvent(BUCKETHIT);

	public static final Identifier STONEHIT = new Identifier("pvzmod:stone.hit");
	public static SoundEvent STONEHITEVENT = new SoundEvent(STONEHIT);

	public static final Identifier SNOWPEASHOOT = new Identifier("pvzmod:snowpea.shoot");
	public static SoundEvent SNOWPEASHOOTEVENT = new SoundEvent(SNOWPEASHOOT);
	public static final Identifier SNOWPEAHIT = new Identifier("pvzmod:snowpea.hit");
	public static SoundEvent SNOWPEAHITEVENT = new SoundEvent(SNOWPEAHIT);

	public static final Identifier MUSHROOMSHOOT = new Identifier("pvzmod:mushroom.shoot");
	public static SoundEvent MUSHROOMSHOOTEVENT = new SoundEvent(MUSHROOMSHOOT);

	public static final Identifier FUMESHROOMSHOOT = new Identifier("pvzmod:fumeshroom.shoot");
	public static SoundEvent FUMESHROOMSHOOTEVENT = new SoundEvent(FUMESHROOMSHOOT);

	public static final Identifier LIGHTNINGSHOOT = new Identifier("pvzmod:lightning.shoot");
	public static SoundEvent LIGHTNINGSHOOTEVENT = new SoundEvent(LIGHTNINGSHOOT);

	public static final Identifier CHERRYBOMBEXPLOSION = new Identifier("pvzmod:cherrybomb.explosion");
	public static SoundEvent CHERRYBOMBEXPLOSIONEVENT = new SoundEvent(CHERRYBOMBEXPLOSION);
	public static final Identifier JALAPENOEXPLOSION = new Identifier("pvzmod:jalapeno.explosion");
	public static SoundEvent JALAPENOEXPLOSIONEVENT = new SoundEvent(JALAPENOEXPLOSION);
	public static final Identifier POTATOMINEEXPLOSION = new Identifier("pvzmod:potatomine.explosion");
	public static SoundEvent POTATOMINEEXPLOSIONEVENT = new SoundEvent(POTATOMINEEXPLOSION);
	public static final Identifier DOOMSHROOMEXPLOSION = new Identifier("pvzmod:doomshroom.explosion");
	public static SoundEvent DOOMSHROOMEXPLOSIONEVENT = new SoundEvent(DOOMSHROOMEXPLOSION);
	public static final Identifier ICEBERGEXPLOSION = new Identifier("pvzmod:iceberg.explosion");
	public static SoundEvent ICEBERGEXPLOSIONEVENT = new SoundEvent(ICEBERGEXPLOSION);
	public static final Identifier EMPEACHEXPLOSION = new Identifier("pvzmod:empeach.explosion");
	public static SoundEvent EMPEACHEXPLOSIONEVENT = new SoundEvent(EMPEACHEXPLOSION);

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

	public static final Identifier POPLIMB = new Identifier("pvzmod:pop.limb");
	public static SoundEvent POPLIMBEVENT = new SoundEvent(POPLIMB);

	public static final Identifier POPHEAD = new Identifier("pvzmod:pop.head");
	public static SoundEvent POPHEADEVENT = new SoundEvent(POPHEAD);

	public static final Identifier POLEVAULT = new Identifier("pvzmod:polevaulting.vault");
	public static SoundEvent POLEVAULTEVENT = new SoundEvent(POLEVAULT);

	public static final Identifier DOLPHINJUMP = new Identifier("pvzmod:dolphin.jump");
	public static SoundEvent DOLPHINJUMPEVENT = new SoundEvent(DOLPHINJUMP);

	public static final Identifier GARGANTUARSMASH = new Identifier("pvzmod:gargantuar.smash");
	public static SoundEvent GARGANTUARSMASHEVENT = new SoundEvent(GARGANTUARSMASH);

	public static final Identifier IMPLAUNCH = new Identifier("pvzmod:imp.launch");
	public static SoundEvent IMPLAUNCHEVENT = new SoundEvent(IMPLAUNCH);

	public static final Identifier IMPANNOUNCER = new Identifier("pvzmod:imp.announcer");
	public static SoundEvent IMPANNOUNCEREVENT = new SoundEvent(IMPANNOUNCER);

	public static final Identifier IMPANNOUNCERALT = new Identifier("pvzmod:imp.announcer.alt");
	public static SoundEvent IMPANNOUNCERALTEVENT = new SoundEvent(IMPANNOUNCERALT);

	public static final Identifier KNIGHTTRANSFORM = new Identifier("pvzmod:knight.transform");
	public static SoundEvent KNIGHTTRANSFORMEVENT = new SoundEvent(KNIGHTTRANSFORM);

	public static final Identifier ZOMBIEMOAN = new Identifier("pvzmod:zombie.moan");
	public static SoundEvent ZOMBIEMOANEVENT = new SoundEvent(ZOMBIEMOAN);

	public static final Identifier DOLPHINWATER = new Identifier("pvzmod:dolphin.water");
	public static SoundEvent DOLPHINWATEREVENT = new SoundEvent(DOLPHINWATER);

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
		ModItems.setSeedPacketList();
		ModBlocks.registerBlocks();
		GeckoLib.initialize();
		PvZEntitySpawn.addEntitySpawn();
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "ice"), ICE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "frozen"), FROZEN);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "stun"), STUN);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "disable"), DISABLE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "warm"), WARM);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "wet"), WET);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "pvzpoison"), PVZPOISON);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "acid"), ACID);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.LOOTNUGGET, LOOTNUGGETEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.LOOTDIAMOND, LOOTDIAMONDEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.LOOTGIFT, LOOTGIFTDEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEASHOOT, PEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEAHIT, PEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FIREPEAHIT, FIREPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CONEHIT, CONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.BUCKETHIT, BUCKETHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.STONEHIT, STONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEASHOOT, SNOWPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEAHIT, SNOWPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.MUSHROOMSHOOT, MUSHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FUMESHROOMSHOOT, FUMESHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.LIGHTNINGSHOOT, LIGHTNINGSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHERRYBOMBEXPLOSION, CHERRYBOMBEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.JALAPENOEXPLOSION, JALAPENOEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POTATOMINEEXPLOSION, POTATOMINEEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.DOOMSHROOMEXPLOSION, DOOMSHROOMEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ICEBERGEXPLOSION, ICEBERGEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.EMPEACHEXPLOSION, EMPEACHEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SQUASHHUM, SQUASHHUMEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHOMPERBITE, CHOMPERBITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVEBUSTEREATING, GRAVEBUSTEREATINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.HYPNOTIZING, HYPNOTIZINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVERISING, GRAVERISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ENTITYRISING, ENTITYRISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PLANTPLANTED, PLANTPLANTEDEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SUNDROP, SUNDROPEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEBITE, ZOMBIEBITEEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POPLIMB, POPLIMBEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POPHEAD, POPHEADEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POLEVAULT, POLEVAULTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.DOLPHINJUMP, DOLPHINJUMPEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GARGANTUARSMASH, GARGANTUARSMASHEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.IMPLAUNCH, IMPLAUNCHEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.IMPANNOUNCER, IMPANNOUNCEREVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.IMPANNOUNCERALT, IMPANNOUNCERALTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.KNIGHTTRANSFORM, KNIGHTTRANSFORMEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEMOAN, ZOMBIEMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.IMPMOAN, IMPMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.NEWSPAPERANGRY, NEWSPAPERANGRYEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.DOLPHINWATER, DOLPHINWATEREVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GARGANTUARMOAN, GARGANTUARMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SILENCE, SILENCEVENET);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEDANCING, ZOMBIEDANCINGEVENT);
	}
}
