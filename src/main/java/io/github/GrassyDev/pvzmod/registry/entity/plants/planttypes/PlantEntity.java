package io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.PeashooterSeeds;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.SunflowerSeeds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public abstract class PlantEntity extends GolemEntity {

	public boolean onWater;

	protected PlantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public ActionResult addPlants(PlayerEntity player, Hand hand){
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		SoundEvent sound = null;
		boolean itemCooldown = player.getItemCooldownManager().isCoolingDown(item);
		if (this instanceof LilyPadEntity) {
			if (onWater) {
				sound = SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
			} else {
				sound = PvZCubed.PLANTPLANTEDEVENT ;
			}
		}
		if (itemStack.isOf(ModItems.PEASHOOTER_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				PeashooterEntity plantEntity = (PeashooterEntity) PvZEntity.PEASHOOTER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, 0.75F, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.PEASHOOTER_SEED_PACKET, PeashooterSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}
		if (itemStack.isOf(ModItems.SUNFLOWER_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				SunflowerEntity plantEntity = (SunflowerEntity) PvZEntity.SUNFLOWER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, 0.75F, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.SUNFLOWER_SEED_PACKET, SunflowerSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}
		else {
			return ActionResult.CONSUME;
		}
	}
}
