package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.newspaper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.HypnoPvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;
import static io.github.GrassyDev.pvzmod.PvZCubed.NEWSPAPERANGRYEVENT;

public class HypnoNewspaperEntity extends HypnoZombieEntity implements IAnimatable {

	private MobEntity owner;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "walkingcontroller";
	private boolean speedUp;

	public boolean speedSwitch;

	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));
	public static final UUID MAX_STRENGTH_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	public HypnoNewspaperEntity(EntityType<? extends HypnoNewspaperEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
		EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		EntityAttributeInstance maxStrengthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
		maxStrengthAttribute.removeModifier(MAX_STRENGTH_UUID);
	}

	static {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 30) {
			this.speedUp = true;
		}
		else if (status == 31) {
			this.speedUp = false;
		}
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
		if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
			if (this.speedUp){
				event.getController().setAnimation(new AnimationBuilder().loop("newspaper.angry"));
				event.getController().setAnimationSpeed(2);
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("newspaper.walking"));
				event.getController().setAnimationSpeed(0.75);
			}
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("newspaper.idle"));
			event.getController().setAnimationSpeed(1);
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new HypnoZombieEntity.AttackGoal());
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 1));
		this.initCustomGoals();
	}

	protected void initCustomGoals() {
		this.targetSelector.add(2, new HypnoNewspaperEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1, true));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
		}));
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void mobTick() {
		super.mobTick();
		EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		EntityAttributeInstance maxStrengthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		if (this.getTarget() != null && this.getHealth() <= 50){
			this.world.sendEntityStatus(this, (byte) 30);
			if (this.speedSwitch) {
				this.playSound(NEWSPAPERANGRYEVENT, 1, 1);
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
				maxStrengthAttribute.removeModifier(MAX_STRENGTH_UUID);
				this.speedSwitch = false;
			}
		}
		else {
			this.world.sendEntityStatus(this, (byte) 31);
			if (!this.speedSwitch){
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
				maxStrengthAttribute.removeModifier(MAX_STRENGTH_UUID);
				maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.15D));
				maxStrengthAttribute.addPersistentModifier(createStrengthModifier(-7D));
				this.speedSwitch = true;
			}
		}
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static EntityAttributeModifier createSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static EntityAttributeModifier createStrengthModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_STRENGTH_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static DefaultAttributeContainer.Builder createHypnoNewspaperAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 14.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 65D);
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
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
			return HypnoNewspaperEntity.this.owner != null && HypnoNewspaperEntity.this.owner.getTarget() != null && this.canTrack(HypnoNewspaperEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
		}

		public void start() {
			HypnoNewspaperEntity.this.setTarget(HypnoNewspaperEntity.this.owner.getTarget());
			super.start();
		}
	}
}
