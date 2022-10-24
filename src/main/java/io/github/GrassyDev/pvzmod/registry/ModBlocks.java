package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class ModBlocks {

    public static final Block GRASS_TILE = new Block(QuiltBlockSettings
            .of(Material.SOIL)
			.requiresTool()
            .sounds(BlockSoundGroup.GRASS)
            .strength(1.9f, 1200f));


    public static final Block DARK_GRASS_TILE = new Block(QuiltBlockSettings
            .of(Material.SOIL)
			.requiresTool()
            .sounds(BlockSoundGroup.GRASS)
            .strength(1.9f, 1200f));

    public static final Block NIGHT_TILE = new Block(QuiltBlockSettings
            .of(Material.STONE)
			.requiresTool()
            .sounds(BlockSoundGroup.STONE)
            .strength(1.9f, 1200f));


    public static final Block DARK_NIGHT_TILE = new Block(QuiltBlockSettings
            .of(Material.STONE)
			.requiresTool()
            .sounds(BlockSoundGroup.STONE)
            .strength(1.9f, 1200f));


	public static final Block PREMIUM_TILE = new Block(QuiltBlockSettings
			.of(Material.AMETHYST)
			.requiresTool()
			.sounds(BlockSoundGroup.AMETHYST_BLOCK)
			.strength(1.9f, 1200f)
			.luminance(3));

    public static void registerBlocks(){

        Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"grass_tile"), GRASS_TILE);
        Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_grass_tile"), DARK_GRASS_TILE);
        Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"night_tile"), NIGHT_TILE);
        Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"dark_night_tile"), DARK_NIGHT_TILE);
		Registry.register(Registry.BLOCK, new Identifier(PvZCubed.MOD_ID,"premium_tile"), PREMIUM_TILE);

    }

}
