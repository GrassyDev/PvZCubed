package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.UnarmedPotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.*;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;

public class GargantuarEntity extends HostileEntity implements IAnimatable {
    private MobEntity owner;
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "walkingcontroller";
    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER;
    private final BreakDoorGoal breakDoorsGoal;
    private boolean canBreakDoors;
    private int attackTicksLeft;
	private int animationTicksLeft;
	private int launchAnimation;
    public boolean firstAttack;
    public boolean inAnimation;
	public boolean inLaunchAnimation;
	private boolean canLaunch = true;

	public GargantuarEntity(EntityType<? extends GargantuarEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.breakDoorsGoal = new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER);
        this.experiencePoints = 12;
        this.firstAttack = true;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (inLaunchAnimation){
			event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.throw", false));
		}
		else if (inAnimation){
			event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.smash", false));
		}
        else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.walk", true));
        }else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("gargantuar.idle", true));
        }
        return PlayState.CONTINUE;
    }

    public GargantuarEntity(World world) {
        this(PvZEntity.GARGANTUAR, world);
    }

        protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
            this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
        this.initCustomGoals();
    }

    protected void initCustomGoals() {
        this.targetSelector.add(2, new GargantuarEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new GargantuarEntity.AttackGoal());
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.targetSelector.add(1, new TargetGoal<>(this, PuffshroomEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, UnarmedPotatomineEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, PotatomineEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, ReinforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, EnforceEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, ContainEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, HypnoshroomEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnchantEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PlayerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AppeaseEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, PepperEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, WinterEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, BombardEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, AilmentEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, EnlightenEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, FilamentEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoZombieEntity.class, false, true));
		this.targetSelector.add(1, new TargetGoal<>(this, HypnoSummonerEntity.class, false, true));
    }

    public static DefaultAttributeContainer.Builder createGargantuarAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 540D);
    }

	private class AttackGoal extends PvZombieAttackGoal {
		public AttackGoal() {
			super(GargantuarEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = GargantuarEntity.this.getWidth() - 0.1F;
			return (double)(f * 4F * f * 4F + entity.getWidth());
		}
	}

    public MobEntity getOwner() {
        return this.owner;
    }

    public void setOwner(MobEntity owner) {
        this.owner = owner;
    }

    public boolean canBreakDoors() {
        return this.canBreakDoors;
    }

    public void setCanBreakDoors(boolean canBreakDoors) {
        if (this.shouldBreakDoors() && NavigationConditions.hasMobNavigation(this)) {
            if (this.canBreakDoors != canBreakDoors) {
                this.canBreakDoors = canBreakDoors;
                ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(canBreakDoors);
                if (canBreakDoors) {
                    this.goalSelector.add(1, this.breakDoorsGoal);
                } else {
                    this.goalSelector.remove(this.breakDoorsGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.remove(this.breakDoorsGoal);
            this.canBreakDoors = false;
        }

    }

    protected boolean shouldBreakDoors() {
        return true;
    }

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

            /**if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE) {
                this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                HypnoFootballEntity hypnoFootballEntity = (HypnoFootballEntity) PvZEntity.HYPNOFOOTBALL.create(world);
                hypnoFootballEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                hypnoFootballEntity.initialize(serverWorld, world.getLocalDifficulty(hypnoFootballEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
                hypnoFootballEntity.setAiDisabled(this.isAiDisabled());
				hypnoFootballEntity.setHealth(this.getHealth());
                if (this.hasCustomName()) {
                    hypnoFootballEntity.setCustomName(this.getCustomName());
                    hypnoFootballEntity.setCustomNameVisible(this.isCustomNameVisible());
                }

                hypnoFootballEntity.setPersistent();
                serverWorld.spawnEntityAndPassengers(hypnoFootballEntity);
                this.remove(RemovalReason.DISCARDED);
            }**/

            return true;
        }
    }

    protected SoundEvent getAmbientSound() {
        return PvZCubed.ZOMBIEMOANEVENT;
    }

    protected SoundEvent getHurtSound() {
        return PvZCubed.SILENCEVENET;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("CanBreakDoors", this.canBreakDoors());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCanBreakDoors(nbt.getBoolean("CanBreakDoors"));
    }

	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.inLaunchAnimation) {
			if (this.firstAttack && this.animationTicksLeft <= 0) {
				this.animationTicksLeft = 90;
				this.firstAttack = false;
			}
			else if (this.animationTicksLeft == 40) {
				if (this.hasStatusEffect(PvZCubed.ICE)) {
					float f = 720f;
					boolean bl = target.damage(DamageSource.mob(this), f);
					if (bl && this.squaredDistanceTo(target) < 4D) {
						this.applyDamageEffects(this, target);
					}
					this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 1F, 1.0F);
					return bl;
				} else {
					float f = 360f;
					boolean bl = target.damage(DamageSource.mob(this), f);
					if (bl && this.squaredDistanceTo(target) < 4D) {
						this.applyDamageEffects(this, target);
					}
					this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 0.5F, 1.0F);
					return bl;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	public boolean tryLaunch(Entity target){
		if (this.canLaunch && launchAnimation == 20){
			ImpEntity imp = new ImpEntity(PvZEntity.IMP, this.world);
			double d = this.squaredDistanceTo(target);
			float df = (float) d;
			double e = target.getX() - this.getX();
			double f = target.getY() - this.getY();
			double g = target.getZ() - this.getZ();
			float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
			imp.setVelocity(e * (double) h, f * (double) h, g * (double) h, 2.25F, 0F);
			imp.updatePosition(this.getX(), this.getY() + 3.95D, this.getZ());
			imp.setOwner(this);
			if (target.isAlive()) {
				this.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
				this.world.spawnEntity(imp);
				this.canLaunch = false;
			}
		}
		return false;
	}

    @Override
    public void registerControllers(AnimationData data)
    {
        AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory()
    {
        return this.factory;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        float f = difficulty.getClampedLocalDifficulty();

        if (entityData instanceof GargantuarEntity.ZombieData) {
            GargantuarEntity.ZombieData zombieData = (GargantuarEntity.ZombieData)entityData;

            this.setCanBreakDoors(this.shouldBreakDoors() && this.random.nextFloat() < f * 0.1F);
        }
        return (EntityData)entityData;
    }

    static {
        DOOR_BREAK_DIFFICULTY_CHECKER = (difficulty) -> {
            return difficulty == Difficulty.HARD;
        };
    }

    public static class ZombieData implements EntityData {

        public ZombieData(boolean baby, boolean bl) {
        }
    }

    public static boolean canGargantuarSpawn(EntityType<GargantuarEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 55;
    }

	public void mobTick() {
		super.mobTick();
		if (this.animationTicksLeft == 40 && getTarget() != null && !inLaunchAnimation) {
			this.firstAttack = true;
			tryAttack(getTarget());
		}
		else if (getTarget() == null){
			this.firstAttack = true;
		}
		if (this.animationTicksLeft > 0) {
			--this.animationTicksLeft;
			this.world.sendEntityStatus(this, (byte) 13);
		}
		else{
			this.world.sendEntityStatus(this, (byte) 12);
		}
		if (this.getHealth() <= 360 && getTarget() != null && this.canLaunch && !this.inLaunchAnimation) {
			this.launchAnimation = 50;
			this.inLaunchAnimation = true;
			this.world.sendEntityStatus(this, (byte) 44);
		}
		if (this.launchAnimation > 0) {
			--launchAnimation;
			tryLaunch(getTarget());
			this.inLaunchAnimation = true;
			this.world.sendEntityStatus(this, (byte) 44);
		}
		else {
			this.inLaunchAnimation = false;
			this.world.sendEntityStatus(this, (byte) 43);
		}
	}

	@Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 13) {
            this.inAnimation = true;
        }
		else if (status == 12) {
			this.inAnimation = false;
		}
		if (status == 44) {
			this.inLaunchAnimation = true;
		}
		else if (status == 43) {
			this.inLaunchAnimation = false;
		}
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return GargantuarEntity.this.owner != null && GargantuarEntity.this.owner.getTarget() != null && this.canTrack(GargantuarEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            GargantuarEntity.this.setTarget(GargantuarEntity.this.owner.getTarget());
            super.start();
        }
    }

}
