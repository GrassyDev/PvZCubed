package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.itemclasses.*;
import io.github.GrassyDev.pvzmod.registry.itemclasses.seedpackets.*;
import io.github.GrassyDev.pvzmod.registry.itemclasses.spawneggs.*;
import io.github.GrassyDev.pvzmod.registry.toolclasses.PlantKillingMaterial;
import io.github.GrassyDev.pvzmod.registry.toolclasses.PlantKillingShovel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item ALMANAC = new Item(new Item.Settings().maxCount(1));

    //Items
    public static final Item PLANTFOOD = new Item(new Item.Settings().maxCount(64));
    public static final Item PEA = new PeaItem(new Item.Settings().group(PvZCubed.PLANTS).maxCount(32));
    public static final Item TRIPEA = new TriPeaItem(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item SPORE = new SporeItem(new Item.Settings().group(PvZCubed.PLANTS).maxCount(64));
    public static final Item FUME = new FumeItem(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item SNOWPEAPROJ = new SnowPeaItem(new Item.Settings().group(PvZCubed.PLANTS).maxCount(32));
    public static final Item REPEA = new RePeaItem(new Item.Settings().group(PvZCubed.PLANTS).maxCount(16));
    public static final Item FIREPEA = new FirepeaItem(new Item.Settings().group(PvZCubed.PLANTS).maxCount(16));
    public static final Item CONE = new Item(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item SUN = new Item(new Item.Settings().group(PvZCubed.PLANTS));
    public static final Item SMALLSUN = new Item(new Item.Settings().group(PvZCubed.PLANTS));
    public static final Item LARGESUN = new Item(new Item.Settings().group(PvZCubed.PLANTS));
    public static final Item WAVE_FLAG = new Item(new Item.Settings().group(PvZCubed.ZOMBIES));

    //Plant Spawn
    public static final Item SUNFLOWER_SEED_PACKET = new SunflowerSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item PEASHOOTER_SEED_PACKET = new PeashooterSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item CHERRYBOMB_SEED_PACKET = new CherryBombSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item WALLNUT_SEED_PACKET = new WallnutSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item POTATOMINE_SEED_PACKET = new PotatoMineSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item SNOW_PEA_SEED_PACKET = new SnowpeaSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
	public static final Item FIRE_PEA_SEED_PACKET = new FirepeaSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item CHOMPER_SEED_PACKET = new ChomperSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item REPEATER_SEED_PACKET = new RepeaterSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item SUNSHROOM_SEED_PACKET = new SunshroomSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item PUFFSHROOM_SEED_PACKET = new PuffshroomSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item FUMESHROOM_SEED_PACKET = new FumeshroomSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item GRAVEBUSTER_SEED_PACKET = new GraveBusterSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item HYPNOSHROOM_SEED_PACKET = new HypnoshroomSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item SCAREDYSHROOM_SEED_PACKET = new ScaredyshroomSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item ICESHROOM_SEED_PACKET = new IceshroomSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item DOOMSHROOM_SEED_PACKET = new DoomshroomSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));
    public static final Item THREEPEATER_SEED_PACKET = new ThreepeaterSeeds(new Item.Settings().group(PvZCubed.PLANTS).maxCount(8));

    //Zombie Spawn
    public static final Item BROWNCOATEGG = new BrowncoatEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item FLAGZOMBIEEGG = new FlagzombieEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item CONEHEADEGG = new ConeheadEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item POLEVAULTINGEGG = new PoleVaultingEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item BUCKETHEADEGG = new BucketheadEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item SCREENDOOREGG = new ScreendoorEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item NEWSPAPEREGG = new NewspaperEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item FOOTBALLEGG = new FootballEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item BERSERKEREGG = new BerserkerEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item DANCINGZOMBIEEGG = new DancingZombieEgg(new Item.Settings().group(PvZCubed.ZOMBIES));
    public static final Item BACKUPDANCEREGG = new BackupDancerEgg(new Item.Settings().group(PvZCubed.ZOMBIES));

    //Grave Spawn
    public static final Item BASICGRAVESPAWN = new BasicGraveSpawn(new Item.Settings().group(PvZCubed.GRAVES));
    public static final Item NIGHTGRAVESPAWN = new NightGraveSpawn(new Item.Settings().group(PvZCubed.GRAVES));

    //Blocks
    public static final BlockItem GRASS_TILE = new BlockItem(ModBlocks.GRASS_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
    public static final BlockItem DARK_GRASS_TILE = new BlockItem(ModBlocks.DARK_GRASS_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
    public static final BlockItem NIGHT_TILE = new BlockItem(ModBlocks.NIGHT_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
    public static final BlockItem DARK_NIGHT_TILE = new BlockItem(ModBlocks.DARK_NIGHT_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));
	public static final BlockItem PREMIUM_TILE = new BlockItem(ModBlocks.PREMIUM_TILE, new Item.Settings().group(PvZCubed.PVZBLOCKS));

    //Tools
    public static ToolItem DAVES_SHOVEL = new PlantKillingShovel(PlantKillingMaterial.INSTANCE, 2, 0F, new Item.Settings().group(PvZCubed.PLANTS));


    //addItem
    //addBlock
    public static void registerItems() {

        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"almanac"), ALMANAC);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"plantfood"), PLANTFOOD);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"pea"), PEA);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"tripea"), TRIPEA);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"spore"), SPORE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"fume"), FUME);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"snowpeaproj"), SNOWPEAPROJ);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"repea"), REPEA);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"firepea"), FIREPEA);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"cone"), CONE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sun"), SUN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"smallsun"), SMALLSUN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"largesun"), LARGESUN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"wave_flag"), WAVE_FLAG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"grass_tile"), GRASS_TILE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_grass_tile"), DARK_GRASS_TILE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"night_tile"), NIGHT_TILE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dark_night_tile"), DARK_NIGHT_TILE);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"premium_tile"), PREMIUM_TILE);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sunflower_seed_packet"), SUNFLOWER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"peashooter_seed_packet"), PEASHOOTER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"cherrybomb_seed_packet"), CHERRYBOMB_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"wallnut_seed_packet"), WALLNUT_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"potatomine_seed_packet"), POTATOMINE_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"snowpea_seed_packet"), SNOW_PEA_SEED_PACKET);
		Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"firepea_seed_packet"), FIRE_PEA_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"chomper_seed_packet"), CHOMPER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"repeater_seed_packet"), REPEATER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"sunshroom_seed_packet"), SUNSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"puffshroom_seed_packet"), PUFFSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"fumeshroom_seed_packet"), FUMESHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"gravebuster_seed_packet"), GRAVEBUSTER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"hypnoshroom_seed_packet"), HYPNOSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"scaredyshroom_seed_packet"), SCAREDYSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"iceshroom_seed_packet"), ICESHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"doomshroom_seed_packet"), DOOMSHROOM_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"threepeater_seed_packet"), THREEPEATER_SEED_PACKET);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"browncoat_egg"), BROWNCOATEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"flagzombie_egg"), FLAGZOMBIEEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"conehead_egg"), CONEHEADEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"polevaulting_egg"), POLEVAULTINGEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"buckethead_egg"), BUCKETHEADEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"screendoor_egg"), SCREENDOOREGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"newspaper_egg"), NEWSPAPEREGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"football_egg"), FOOTBALLEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"berserker_egg"), BERSERKEREGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"dancingzombie_egg"), DANCINGZOMBIEEGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"backupdancer_egg"), BACKUPDANCEREGG);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"basicgrave_spawn"), BASICGRAVESPAWN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"nightgrave_spawn"), NIGHTGRAVESPAWN);
        Registry.register(Registry.ITEM, new Identifier(PvZCubed.MOD_ID,"daves_shovel"), DAVES_SHOVEL);

    }

}
