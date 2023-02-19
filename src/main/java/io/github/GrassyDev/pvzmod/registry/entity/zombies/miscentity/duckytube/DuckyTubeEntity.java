package io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday.HypnoGargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.snorkel.HypnoSnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DuckyTubeEntity extends PathAwareEntity implements IAnimatable {
    private MobEntity owner;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "walkingcontroller";
	private boolean submerged;

	public DuckyTubeEntity(EntityType<? extends DuckyTubeEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	static {

	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		Entity passenger = this.getPrimaryPassenger();
		if (passenger instanceof GargantuarEntity || passenger instanceof HypnoGargantuarEntity) {
			event.getController().setAnimation(new AnimationBuilder().loop("ducky.big"));
		}
		else if (passenger instanceof SnorkelEntity || passenger instanceof HypnoSnorkelEntity){
			event.getController().setAnimation(new AnimationBuilder().loop("ducky.invis"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("ducky.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
    }

	public void tick() {
		super.tick();
		if (!this.hasVehicle()){
			kill();
		}
	}

	private void updateSubmergedState() {
		this.submerged = this.isSubmergedIn(FluidTags.WATER);
		double d = this.getEyeY() + 0.1111111119389534;
		BlockPos blockPos = new BlockPos(this.getX(), d, this.getZ());
		FluidState fluidState = this.world.getFluidState(blockPos);
		double e = (double)((float)blockPos.getY() + fluidState.getHeight(this.world, blockPos));
		Entity passenger = this.getPrimaryPassenger();
		if ((e > d) && passenger != null) {
			this.discard();
		}
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	/**public boolean collides() {
		return !this.isRemoved();
	}

	public boolean isCollidable() {
		return this.isAlive();
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public boolean canBeRiddenInWater() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.50F;
	}

	public double getMountedHeightOffset() {
		float f = Math.min(0.25F, this.limbDistance);
		float g = this.limbAngle;
		return (double)this.getHeight() - 0.19 + (double)(0.12F * MathHelper.cos(g * 1.5F) * 2.0F * f);
	}

	@Nullable
	public Entity getPrimaryPassenger() {
		Entity entity = this.getFirstPassenger();
		return entity;
	}

	public Vec3d updatePassengerForDismount(LivingEntity passenger) {
		Vec3d[] vec3ds = new Vec3d[]{getPassengerDismountOffset((double)this.getWidth(), (double)passenger.getWidth(), passenger.getYaw()), getPassengerDismountOffset((double)this.getWidth(), (double)passenger.getWidth(), passenger.getYaw() - 22.5F), getPassengerDismountOffset((double)this.getWidth(), (double)passenger.getWidth(), passenger.getYaw() + 22.5F), getPassengerDismountOffset((double)this.getWidth(), (double)passenger.getWidth(), passenger.getYaw() - 45.0F), getPassengerDismountOffset((double)this.getWidth(), (double)passenger.getWidth(), passenger.getYaw() + 45.0F)};
		Set<BlockPos> set = Sets.newLinkedHashSet();
		double d = this.getBoundingBox().maxY;
		double e = this.getBoundingBox().minY - 0.5;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Vec3d[] var9 = vec3ds;
		int var10 = vec3ds.length;

		for(int var11 = 0; var11 < var10; ++var11) {
			Vec3d vec3d = var9[var11];
			mutable.set(this.getX() + vec3d.x, d, this.getZ() + vec3d.z);

			for(double f = d; f > e; --f) {
				set.add(mutable.toImmutable());
				mutable.move(Direction.DOWN);
			}
		}

		Iterator var17 = set.iterator();

		while(true) {
			BlockPos blockPos;
			double g;
			do {
				do {
					if (!var17.hasNext()) {
						return new Vec3d(this.getX(), this.getBoundingBox().maxY, this.getZ());
					}

					blockPos = (BlockPos)var17.next();
				} while(this.world.getFluidState(blockPos).isIn(FluidTags.WATER));

				g = this.world.getDismountHeight(blockPos);
			} while(!Dismounting.canDismountInBlock(g));

			Vec3d vec3d2 = Vec3d.ofCenter(blockPos, g);
			UnmodifiableIterator var14 = passenger.getPoses().iterator();

			while(var14.hasNext()) {
				EntityPose entityPose = (EntityPose)var14.next();
				Box box = passenger.getBoundingBox(entityPose);
				if (Dismounting.canPlaceEntityAt(this.world, passenger, box.offset(vec3d2))) {
					passenger.setPose(entityPose);
					return vec3d2;
				}
			}
		}
	}

	private void updateFloating() {
		if (this.isInsideWaterOrBubbleColumn()) {
			ShapeContext shapeContext = ShapeContext.of(this);
			if (shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) && !this.world.getFluidState(this.getBlockPos().up()).isIn(FluidTags.WATER)) {
				this.onGround = true;
			}
		}
	}

	protected boolean canAddPassenger(Entity passenger) {
		return !this.hasPassengers() && !this.isSubmergedIn(FluidTags.WATER);
	}

	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		if (world.getBlockState(pos).getFluidState().isIn(FluidTags.WATER)) {
			return 10.0F;
		} else {
			return this.isInsideWaterOrBubbleColumn() ? Float.NEGATIVE_INFINITY : 0.0F;
		}
	}

	**/public static DefaultAttributeContainer.Builder createDuckyTubeAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 27D);
    }/**

	protected SoundEvent getAmbientSound() {
		return PvZCubed.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	protected SoundEvent getStepSound() {
		return PvZCubed.SILENCEVENET;
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}**/


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (!(this.world instanceof ServerWorld)) {
            return false;
        } else {
            ServerWorld serverWorld = (ServerWorld)this.world;
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
                this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                BrowncoatEntity hypnotizedZombie = (BrowncoatEntity) PvZEntity.BROWNCOATHYPNO.create(world);
                hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
                if (this.hasCustomName()) {
                    hypnotizedZombie.setCustomName(this.getCustomName());
                    hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnotizedZombie.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
                this.remove(RemovalReason.DISCARDED);
            }

            return true;
        }
    }

	public boolean onKilledOther(ServerWorld serverWorld, LivingEntity livingEntity) {
		super.onKilledOther(serverWorld, livingEntity);
		boolean bl = super.onKilledOther(serverWorld, livingEntity);
		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && livingEntity instanceof VillagerEntity) {
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return bl;
			}

			VillagerEntity villagerEntity = (VillagerEntity) livingEntity;
			ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
			zombieVillagerEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), (NbtCompound) null);
			zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
			zombieVillagerEntity.setGossipData((NbtElement) villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
			zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
			zombieVillagerEntity.setXp(villagerEntity.getExperience());
			if (!this.isSilent()) {
				serverWorld.syncWorldEvent((PlayerEntity) null, 1026, this.getBlockPos(), 0);
			}
		}

		return bl;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	/**class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return DuckyTubeEntity.this.owner != null && DuckyTubeEntity.this.owner.getTarget() != null && this.canTrack(DuckyTubeEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            DuckyTubeEntity.this.setTarget(DuckyTubeEntity.this.owner.getTarget());
            super.start();
        }
    }**/
}
