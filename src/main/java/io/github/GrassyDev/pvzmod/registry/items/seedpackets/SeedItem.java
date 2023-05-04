package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class SeedItem extends Item {
	public SeedItem(Settings settings) {
		super(settings);
	}

	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}
}
