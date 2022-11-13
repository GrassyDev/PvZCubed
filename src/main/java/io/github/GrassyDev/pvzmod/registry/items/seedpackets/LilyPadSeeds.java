package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilyPadEntity;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Iterator;
import java.util.List;

public class LilyPadSeeds extends Item {
    public LilyPadSeeds(Settings settings) {
        super(settings);
    }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				if (world instanceof ServerWorld) {
					LilyPadEntity lilypadEntity = this.createEntity(world, hitResult);
					lilypadEntity.setYaw(user.getYaw());
					if (!world.isSpaceEmpty(lilypadEntity, lilypadEntity.getBoundingBox())) {
						return TypedActionResult.fail(itemStack);
					} else {
						if (!world.isClient) {
							world.spawnEntity(lilypadEntity);
							world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
							FluidState fluidState = world.getFluidState(lilypadEntity.getBlockPos().add(0, -0.25, 0));
							if (fluidState.getFluid() == Fluids.WATER) {
								world.playSound((PlayerEntity) null, lilypadEntity.getX(), lilypadEntity.getY(), lilypadEntity.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, SoundCategory.BLOCKS, 0.25f, 0.8F);
							} else {
								world.playSound((PlayerEntity) null, lilypadEntity.getX(), lilypadEntity.getY(), lilypadEntity.getZ(), PvZCubed.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.6f, 0.8F);
							}
							if (!user.getAbilities().creativeMode) {
								itemStack.decrement(1);
								user.getItemCooldownManager().set(this, 50);
							}
						}

						user.incrementStat(Stats.USED.getOrCreateStat(this));
						return TypedActionResult.success(itemStack, world.isClient());
					}
				}
			} else {
				return TypedActionResult.pass(itemStack);
			}
		}
		return TypedActionResult.pass(itemStack);
	}

	private LilyPadEntity createEntity(World world, HitResult hitResult) {
		return (LilyPadEntity)(new LilyPadEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z));
	}

}
