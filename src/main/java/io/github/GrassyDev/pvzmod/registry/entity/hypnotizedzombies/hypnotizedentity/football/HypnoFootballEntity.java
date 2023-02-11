package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.football;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoPvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class HypnoFootballEntity extends HypnoZombieEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";
	private MobEntity owner;
	private int attackTicksLeft;
	public boolean firstAttack;
	public boolean tackle;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public HypnoFootballEntity(EntityType<? extends HypnoFootballEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.firstAttack = true;
	}

	static {

	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Tackle", this.getTackleStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Tackle"));
	}

	static {

	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(HypnoFootballEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum TackleStage {
		TACKLING(true),
		EATING(false);

		TackleStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getTackleStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setTackleStage(HypnoFootballEntity.TackleStage tackleStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tackleStage.getId());
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
		Entity vehicle = this.getVehicle();
		if (vehicle instanceof DuckyTubeEntity) {
			event.getController().setAnimation(new AnimationBuilder().loop("football.ducky"));
		}else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (!this.getTackleStage()) {
					event.getController().setAnimation(new AnimationBuilder().loop("football.running"));
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("football.tackle"));
				}
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("football.idle"));
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new HypnoZombieEntity.AttackGoal(this));
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.targetSelector.add(2, new HypnoFootballEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster ;
		}));
	}

	public boolean tryAttack(Entity target) {
		int i = this.attackTicksLeft;
		if (!this.hasStatusEffect(PvZCubed.FROZEN)) {
			if (this.getTackleStage() && this.getVehicle() != null) {
				if (i <= 0) {
					if (this.hasStatusEffect(PvZCubed.ICE)) {
						this.attackTicksLeft = 20;
						this.world.sendEntityStatus(this, (byte) 4);
						float f = 360f;
						boolean bl = target.damage(DamageSource.mob(this), f);
						if (bl) {
							this.applyDamageEffects(this, target);
						}
						this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
						this.setTackleStage(HypnoFootballEntity.TackleStage.EATING);
						return bl;
					} else {
						this.attackTicksLeft = 20;
						float f = 180f;
						boolean bl = target.damage(DamageSource.mob(this), f);
						if (bl) {
							this.applyDamageEffects(this, target);
						}
						this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
						this.setTackleStage(HypnoFootballEntity.TackleStage.EATING);
						return bl;
					}
				} else {
					return false;
				}
			} else {
				if (i <= 0) {
					this.attackTicksLeft = 20;
					this.world.sendEntityStatus(this, (byte) 4);
					float f = this.getAttackDamage();
					boolean bl = target.damage(DamageSource.mob(this), f);
					if (bl) {
						this.applyDamageEffects(this, target);
					}
					return bl;
				} else {
					return false;
				}
			}
		}  else {
			return false;
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tickMovement() {
		super.tickMovement();
		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createHypnoFootballAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 167D);
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
	}

	private float getAttackDamage(){
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.ZOMBIEBITEEVENT;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

		public TrackOwnerTargetGoal(PathAwareEntity mob) {
			super(mob, false);
		}

		public boolean canStart() {
			return HypnoFootballEntity.this.owner != null && HypnoFootballEntity.this.owner.getTarget() != null && this.canTrack(HypnoFootballEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
		}

		public void start() {
			HypnoFootballEntity.this.setTarget(HypnoFootballEntity.this.owner.getTarget());
			super.start();
		}
	}
}
