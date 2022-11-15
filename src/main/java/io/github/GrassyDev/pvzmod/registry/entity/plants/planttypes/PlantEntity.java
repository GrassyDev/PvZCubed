package io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.cherrybomb.CherrybombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.doomshroom.DoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.flamingpea.FlamingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.iceshroom.IceshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.perfoomshroom.PerfoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.repeater.RepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.scaredyshroom.ScaredyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowpea.SnowpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowqueenpea.SnowqueenpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.squash.SquashEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.threepeater.ThreepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.wallnutentity.WallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.*;
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
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public abstract class PlantEntity extends GolemEntity {

	public boolean onWater;
	private float volume = 0.6f;

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

		/**PEASHOOTER**/
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
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.PEASHOOTER_SEED_PACKET, PeashooterSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**SUNFLOWER**/
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
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.SUNFLOWER_SEED_PACKET, SunflowerSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**CHERRY BOMB**/
		if (itemStack.isOf(ModItems.CHERRYBOMB_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				CherrybombEntity plantEntity = (CherrybombEntity) PvZEntity.CHERRYBOMB.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.CHERRYBOMB_SEED_PACKET, CherryBombSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**WALLNUT**/
		if (itemStack.isOf(ModItems.WALLNUT_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				WallnutEntity plantEntity = (WallnutEntity) PvZEntity.WALLNUT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.WALLNUT_SEED_PACKET, WallnutSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**SNOW PEA**/
		if (itemStack.isOf(ModItems.SNOW_PEA_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				SnowpeaEntity plantEntity = (SnowpeaEntity) PvZEntity.SNOWPEA.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.SNOW_PEA_SEED_PACKET, SnowpeaSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**CHOMPER**/
		if (itemStack.isOf(ModItems.CHOMPER_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				ChomperEntity plantEntity = (ChomperEntity) PvZEntity.CHOMPER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.CHOMPER_SEED_PACKET, ChomperSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**REPEATER**/
		if (itemStack.isOf(ModItems.REPEATER_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				RepeaterEntity plantEntity = (RepeaterEntity) PvZEntity.REPEATER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.REPEATER_SEED_PACKET, RepeaterSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**PUFF-SHROOM**/
		if (itemStack.isOf(ModItems.PUFFSHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				PuffshroomEntity plantEntity = (PuffshroomEntity) PvZEntity.PUFFSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.PUFFSHROOM_SEED_PACKET, PuffshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**SUN-SHROOM**/
		if (itemStack.isOf(ModItems.SUNSHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				SunshroomEntity plantEntity = (SunshroomEntity) PvZEntity.SUNSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.SUNSHROOM_SEED_PACKET, SunshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**FUME-SHROOM**/
		if (itemStack.isOf(ModItems.FUMESHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				FumeshroomEntity plantEntity = (FumeshroomEntity) PvZEntity.FUMESHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.FUMESHROOM_SEED_PACKET, FumeshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**HYPNO-SHROOM**/
		if (itemStack.isOf(ModItems.HYPNOSHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				HypnoshroomEntity plantEntity = (HypnoshroomEntity) PvZEntity.HYPNOSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.HYPNOSHROOM_SEED_PACKET, HypnoshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**SCAREDY-SHROOM**/
		if (itemStack.isOf(ModItems.SCAREDYSHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				ScaredyshroomEntity plantEntity = (ScaredyshroomEntity) PvZEntity.SCAREDYSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.SCAREDYSHROOM_SEED_PACKET, ScaredyshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**ICE-SHROOM**/
		if (itemStack.isOf(ModItems.ICESHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				IceshroomEntity plantEntity = (IceshroomEntity) PvZEntity.ICESHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.ICESHROOM_SEED_PACKET, IceshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**DOOMSHROOM**/
		if (itemStack.isOf(ModItems.DOOMSHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				DoomshroomEntity plantEntity = (DoomshroomEntity) PvZEntity.DOOMSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.DOOMSHROOM_SEED_PACKET, DoomshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**SQUASH**/
		if (itemStack.isOf(ModItems.SQUASH_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				SquashEntity plantEntity = (SquashEntity) PvZEntity.SQUASH.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.SQUASH_SEED_PACKET, SquashSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**THREEPEATER**/
		if (itemStack.isOf(ModItems.THREEPEATER_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				ThreepeaterEntity plantEntity = (ThreepeaterEntity) PvZEntity.THREEPEATER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.THREEPEATER_SEED_PACKET, ThreepeaterSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**SNOW QUEEN PEA**/
		if (itemStack.isOf(ModItems.SNOW_QUEENPEA_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				SnowqueenpeaEntity plantEntity = (SnowqueenpeaEntity) PvZEntity.SNOWQUEENPEA.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.SNOW_QUEENPEA_SEED_PACKET, SnowqueenpeaSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**PERFOOM-SHROOM**/
		if (itemStack.isOf(ModItems.PERFOOMSHROOM_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				PerfoomshroomEntity plantEntity = (PerfoomshroomEntity) PvZEntity.PERFOOMSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.PERFOOMSHROOM_SEED_PACKET, PerfoomshroomSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**PEAPOD**/
		if (itemStack.isOf(ModItems.PEAPOD_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				PeapodEntity plantEntity = (PeapodEntity) PvZEntity.PEAPOD.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.PEAPOD_SEED_PACKET, PeaPodSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}

		/**FIRE PEA**/
		if (itemStack.isOf(ModItems.FIRE_PEA_SEED_PACKET) && !itemCooldown) {
			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				FlamingpeaEntity plantEntity = (FlamingpeaEntity) PvZEntity.FLAMINGPEA.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
				if (plantEntity == null) {
					return ActionResult.FAIL;
				}

				float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
				plantEntity.refreshPositionAndAngles(plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), f, 0.0F);
				plantEntity.startRiding(this, true);
				world.spawnEntity(plantEntity);
				world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.FIRE_PEA_SEED_PACKET, FirepeaSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}
		else {
			return ActionResult.CONSUME;
		}
	}
}
