package io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.IcebergLettuceSeeds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ScorchedTile extends TileEntity {
	public ScorchedTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 1200){
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/



	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.ICEBERGLETTUCE_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.ICEBERGEXPLOSIONEVENT);
			this.remove(RemovalReason.DISCARDED);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.ICEBERGLETTUCE_SEED_PACKET, IcebergLettuceSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}/**
		if (itemStack.isOf(ModItems.SNOW_PEA_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.SNOWPEAHITEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				SnowpeaEntity plantEntity = (SnowpeaEntity) PvZEntity.SNOWPEA.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.SNOW_PEA_SEED_PACKET, SnowpeaSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (itemStack.isOf(ModItems.ICEBERGPULT_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.SNOWPEAHITEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				IcebergpultEntity plantEntity = (IcebergpultEntity) PvZEntity.ICEBERGPULT.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.ICEBERGPULT_SEED_PACKET, IcebergpultSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (itemStack.isOf(ModItems.ICESHROOM_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.SNOWPEAHITEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				IceshroomEntity plantEntity = (IceshroomEntity) PvZEntity.ICESHROOM.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.ICESHROOM_SEED_PACKET, IceshroomSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (itemStack.isOf(ModItems.TORCHWOOD_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				TorchwoodEntity plantEntity = (TorchwoodEntity) PvZEntity.TORCHWOOD.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);

			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.TORCHWOOD_SEED_PACKET, TorchwoodSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (itemStack.isOf(ModItems.JALAPENO_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				JalapenoEntity plantEntity = (JalapenoEntity) PvZEntity.JALAPENO.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);

			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.JALAPENO_SEED_PACKET, JalapenoSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (itemStack.isOf(ModItems.CHERRYBOMB_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				CherrybombEntity plantEntity = (CherrybombEntity) PvZEntity.CHERRYBOMB.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);

			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.CHERRYBOMB_SEED_PACKET, CherryBombSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}if (itemStack.isOf(ModItems.FIRE_PEA_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				FlamingpeaEntity plantEntity = (FlamingpeaEntity) PvZEntity.FLAMINGPEA.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);

			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.FIRE_PEA_SEED_PACKET, FirepeaSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}if (itemStack.isOf(ModItems.BOMBSEEDLING_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				BombSeedlingEntity plantEntity = (BombSeedlingEntity) PvZEntity.BOMBSEEDLING.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);

			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.BOMBSEEDLING_SEED_PACKET, BombSeedlingSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}if (itemStack.isOf(ModItems.PEPPERPULT_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				PepperpultEntity plantEntity = (PepperpultEntity) PvZEntity.PEPPERPULT.create(world);
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, world.getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);

			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.PEPPERPULT_SEED_PACKET, PepperpultSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}**/
		else {
			return super.interactMob(player, hand);
		}
	}
}
