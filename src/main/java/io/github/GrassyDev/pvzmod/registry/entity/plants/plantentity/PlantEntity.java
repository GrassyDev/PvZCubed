package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater.RepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea.SnowpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom.DoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom.IceshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom.ScaredyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.JalapenoEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut.TallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood.TorchwoodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.scrapped.icebergpult.IcebergpultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.frostbitecaves.pepperpult.PepperpultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter.BeeshooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.shamrock.ShamrockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.dandelionweed.DandelionWeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.perfoomshroom.PerfoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower.BellflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling.BombSeedlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.zapricot.ZapricotEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.*;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
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
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public abstract class PlantEntity extends GolemEntity {

	public boolean onWater;

	public boolean naturalSpawn;

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}

	protected PlantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public void rideLilyPad(LivingEntity livingEntity){
		this.refreshPositionAndAngles(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.bodyYaw, 0.0F);
		this.startRiding(livingEntity);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_ASLEEP, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Asleep", this.getIsAsleep());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_ASLEEP, tag.getBoolean("Asleep"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	protected static final TrackedData<Boolean> DATA_ID_ASLEEP =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public enum IsAsleep {
		FALSE(false),
		TRUE(true);

		IsAsleep(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getIsAsleep() {
		return this.dataTracker.get(DATA_ID_ASLEEP);
	}

	public void setIsAsleep(PlantEntity.IsAsleep asleep) {
		this.dataTracker.set(DATA_ID_ASLEEP, asleep.getId());
	}

	/** ----------------------------------------------------------------------- **/

	public void tick() {
		super.tick();
		Entity vehicle = this.getVehicle();
		if (vehicle instanceof LilyPadEntity){
			vehicle.setBodyYaw(this.bodyYaw);
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		if (!PLANT_LOCATION.get(this.getType()).orElse("normal").equals("flying")){
			RandomGenerator randomGenerator = this.getRandom();
			BlockState blockState = this.getLandingBlockState();
			for (int i = 0; i < 4; ++i) {
				double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				double e = this.getY() + 0.3;
				double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
		super.onDeath(source);
		super.discard();
	}

	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.world.getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_DROP)){
			super.dropLoot(source, causedByPlayer);
		}
	}

	public HitResult amphibiousRaycast(double maxDistance) {
		Vec3d vec3d1 = this.getPos();
		Vec3d vec3d2 = new Vec3d(vec3d1.x, vec3d1.y - maxDistance, vec3d1.z);
		return this.world.raycast(new RaycastContext(vec3d1, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, this));
	}


	public ActionResult addPlants(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		SoundEvent sound = null;
		boolean itemCooldown = player.getItemCooldownManager().isCoolingDown(item);
		if (this instanceof LilyPadEntity lilyPadEntity) {
			if (onWater) {
				sound = SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED;
			} else {
				sound = PvZCubed.PLANTPLANTEDEVENT;
			}
			lilyPadEntity.setPuffshroomPermanency(LilyPadEntity.PuffPermanency.PERMANENT);
		}

		if (this.getPassengerList().isEmpty()) {
			/**PEASHOOTER**/
			float volume = 0.6f;
			if (itemStack.isOf(ModItems.PEASHOOTER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					PeashooterEntity plantEntity = (PeashooterEntity) PvZEntity.PEASHOOTER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
						itemStack.decrement(1);
					}
					;
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.PEASHOOTER_SEED_PACKET, PeashooterSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					if (random <= 0.125) {
						plantEntity.setVariant(SunflowerVariants.LESBIAN);
					}
					else if (random <= 0.25) {
						plantEntity.setVariant(SunflowerVariants.WLW);
					}
					else if (random <= 0.375) {
						plantEntity.setVariant(SunflowerVariants.MLM);
					}
					else {
						plantEntity.setVariant(SunflowerVariants.DEFAULT);
					}
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
					plantEntity.sunProducingTime = 600;
					plantEntity.playSound(PvZCubed.SUNDROPEVENT, 0.5F, 1F);
					plantEntity.dropItem(ModItems.SUN);
					plantEntity.dropItem(ModItems.SUN);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
						itemStack.decrement(1);
					}
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SUNFLOWER_SEED_PACKET, SunflowerSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.CHERRYBOMB_SEED_PACKET, CherryBombSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.WALLNUT_SEED_PACKET, WallnutSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					if (random <= 0.125) {
						plantEntity.setVariant(SnowPeaVariants.BISEXUAL);
					} else if (random <= 0.25) {
						plantEntity.setVariant(SnowPeaVariants.MLM);
					} else {
						plantEntity.setVariant(SnowPeaVariants.DEFAULT);
					}
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SNOW_PEA_SEED_PACKET, SnowpeaSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					if (random <= 0.125) {
						plantEntity.setVariant(ChomperVariants.ENBY);
					} else if (random <= 0.25) {
						plantEntity.setVariant(ChomperVariants.DEMIGIRL);
					} else if (random <= 0.375) {
						plantEntity.setVariant(ChomperVariants.PIRANHAPLANT);
					} else {
						plantEntity.setVariant(ChomperVariants.DEFAULT);
					}
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.CHOMPER_SEED_PACKET, ChomperSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.REPEATER_SEED_PACKET, RepeaterSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(PuffshroomEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.PUFFSHROOM_SEED_PACKET, PuffshroomSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
					plantEntity.sunProducingTime = 100;
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SUNSHROOM_SEED_PACKET, SunshroomSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					if (random <= 0.125) {
						plantEntity.setVariant(FumeshroomVariants.GAY);
					} else if (random <= 0.25) {
						plantEntity.setVariant(FumeshroomVariants.TRANS);
					} else {
						plantEntity.setVariant(FumeshroomVariants.DEFAULT);
					}
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.FUMESHROOM_SEED_PACKET, FumeshroomSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.HYPNOSHROOM_SEED_PACKET, HypnoshroomSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					if (random <= 0.125) {
						plantEntity.setVariant(ScaredyshroomVariants.DEMIBOY);
					} else if (random <= 0.25) {
						plantEntity.setVariant(ScaredyshroomVariants.LINK);
					} else {
						plantEntity.setVariant(ScaredyshroomVariants.DEFAULT);
					}
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SCAREDYSHROOM_SEED_PACKET, ScaredyshroomSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.ICESHROOM_SEED_PACKET, IceshroomSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.DOOMSHROOM_SEED_PACKET, DoomshroomSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					plantEntity.originalVec3d = plantEntity.getPos();
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SQUASH_SEED_PACKET, SquashSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.THREEPEATER_SEED_PACKET, ThreepeaterSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**JALAPENO**/
			if (itemStack.isOf(ModItems.JALAPENO_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					JalapenoEntity plantEntity = (JalapenoEntity) PvZEntity.JALAPENO.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.JALAPENO_SEED_PACKET, JalapenoSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**TORCHWOOD**/
			if (itemStack.isOf(ModItems.TORCHWOOD_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					TorchwoodEntity plantEntity = (TorchwoodEntity) PvZEntity.TORCHWOOD.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.TORCHWOOD_SEED_PACKET, TorchwoodSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**TALLNUT**/
			if (itemStack.isOf(ModItems.TALLNUT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					TallnutEntity plantEntity = (TallnutEntity) PvZEntity.TALLNUT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.TALLNUT_SEED_PACKET, TallnutSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**CABBAGE-PULT**/
			if (itemStack.isOf(ModItems.CABBAGEPULT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					CabbagepultEntity plantEntity = (CabbagepultEntity) PvZEntity.CABBAGEPULT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.CABBAGEPULT_SEED_PACKET, CabbagepultSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**ICEBERG-PULT**/
			if (itemStack.isOf(ModItems.ICEBERGPULT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					IcebergpultEntity plantEntity = (IcebergpultEntity) PvZEntity.ICEBERGPULT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
						itemStack.decrement(1);
					};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.ICEBERGPULT_SEED_PACKET, IcebergpultSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**Beeshooter**/
			if (itemStack.isOf(ModItems.BEESHOOTER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					BeeshooterEntity plantEntity = (BeeshooterEntity) PvZEntity.BEESHOOTER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.BEESHOOTER_SEED_PACKET, BeeshooterSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**SHAMROCK**/
			if (itemStack.isOf(ModItems.SHAMROCK_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					ShamrockEntity plantEntity = (ShamrockEntity) PvZEntity.SHAMROCK.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					if (random <= 0.25) {
						plantEntity.setVariant(ShamrockVariants.PRIDE);
					} else {
						plantEntity.setVariant(ShamrockVariants.DEFAULT);
					}
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SHAMROCK_SEED_PACKET, ShamrockSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**ICEBERG LETTUCE**/
			if (itemStack.isOf(ModItems.ICEBERGLETTUCE_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					IcebergLettuceEntity plantEntity = (IcebergLettuceEntity) PvZEntity.ICEBERGLETTUCE.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(IcebergLettuceEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.ICEBERGLETTUCE_SEED_PACKET, IcebergLettuceSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					if (random <= 0.25) {
						plantEntity.setVariant(PeapodVariants.PLURAL);
					} else {
						plantEntity.setVariant(PeapodVariants.DEFAULT);
					}
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.PEAPOD_SEED_PACKET, PeaPodSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**PEPPER-PULT**/
			if (itemStack.isOf(ModItems.PEPPERPULT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					PepperpultEntity plantEntity = (PepperpultEntity) PvZEntity.PEPPERPULT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.PEPPERPULT_SEED_PACKET, PepperpultSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.FIRE_PEA_SEED_PACKET, FirepeaSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**DANDELION WEED**/
			if (itemStack.isOf(ModItems.DANDELIONWEED_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					DandelionWeedEntity plantEntity = (DandelionWeedEntity) PvZEntity.DANDELIONWEED.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.DANDELIONWEED_SEED_PACKET, DandelionWeedSeeds.cooldown);
					}
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
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.PERFOOMSHROOM_SEED_PACKET, PerfoomshroomSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**SMALL-NUT**/
			if (itemStack.isOf(ModItems.SMALLNUT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SmallNutEntity plantEntity = (SmallNutEntity) PvZEntity.SMALLNUT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(SmallNutEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SMALLNUT_SEED_PACKET, SmallnutSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**BUTTON-SHROOM**/
			if (itemStack.isOf(ModItems.BUTTONSHROOM_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					ButtonshroomEntity plantEntity = (ButtonshroomEntity) PvZEntity.BUTTONSHROOM.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(ButtonshroomEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.BUTTONSHROOM_SEED_PACKET, ButtonshroomSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**BOMB SEEDLING**/
			if (itemStack.isOf(ModItems.BOMBSEEDLING_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					BombSeedlingEntity plantEntity = (BombSeedlingEntity) PvZEntity.BOMBSEEDLING.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(BombSeedlingEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.BOMBSEEDLING_SEED_PACKET, BombSeedlingSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**ZAPRICOT**/
			if (itemStack.isOf(ModItems.ZAPRICOT_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					ZapricotEntity plantEntity = (ZapricotEntity) PvZEntity.ZAPRICOT.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					double random = Math.random();
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(ZapricotEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.ZAPRICOT_SEED_PACKET, ZapricotSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**WEENIE BEANIE**/
			if (itemStack.isOf(ModItems.WEENIEBEANIE_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					WeenieBeanieEntity plantEntity = (WeenieBeanieEntity) PvZEntity.WEENIEBEANIE.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(WeenieBeanieEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.WEENIEBEANIE_SEED_PACKET, WeenieBeanieSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**SUNFLOWER SEED**/
			if (itemStack.isOf(ModItems.SUNFLOWERSEED_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					SunflowerSeedEntity plantEntity = (SunflowerSeedEntity) PvZEntity.SUNFLOWERSEED.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					plantEntity.setPuffshroomPermanency(SunflowerSeedEntity.PuffPermanency.PERMANENT);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
				itemStack.decrement(1);
			};
					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.SUNFLOWERSEED_SEED_PACKET, SunflowerSeedSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}

			/**BELLFLOWER**/
			if (itemStack.isOf(ModItems.BELLFLOWER_SEED_PACKET) && !itemCooldown) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					BellflowerEntity plantEntity = (BellflowerEntity) PvZEntity.BELLFLOWER.create(serverWorld, itemStack.getNbt(), (Text) null, player, this.getBlockPos(), SpawnReason.SPAWN_EGG, true, true);
					if (plantEntity == null) {
						return ActionResult.FAIL;
					}

					float f = (float) MathHelper.floor((MathHelper.wrapDegrees(player.getYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
					plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), f, 0.0F);
					((ServerWorld) world).spawnEntityAndPassengers(plantEntity);
					plantEntity.rideLilyPad(this);
					world.playSound((PlayerEntity) null, plantEntity.getX(), plantEntity.getY(), plantEntity.getZ(), sound, SoundCategory.BLOCKS, volume, 0.8F);
				}
				if (!player.getAbilities().creativeMode) {
					if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
						itemStack.decrement(1);
					}

					if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
						player.getItemCooldownManager().set(ModItems.BELLFLOWER_SEED_PACKET, BellflowerSeeds.cooldown);
					}
				}
				return ActionResult.SUCCESS;
			}
			else {
				return ActionResult.CONSUME;
			}
		}
		else {
			return ActionResult.FAIL;
		}
	}

	public static class PlantData implements EntityData {
		public final boolean tryLilyPad;

		public PlantData(boolean tryLilyPad) {
			this.tryLilyPad = tryLilyPad;
		}
	}
}
