package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeTiers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GardenChallengeEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

    private String controllerName = "gardencontroller";

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public GardenChallengeEntity(EntityType<? extends GardenChallengeEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TIERS, 0);
		this.dataTracker.startTracking(WAVES, 0);
		this.dataTracker.startTracking(WAVEINPROGRESS, false);
		this.dataTracker.startTracking(WAVETICKS, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("tiers", this.getTierCount());
		tag.putInt("waves", this.getWaveCount());
		tag.putBoolean("waveInProgress", this.getWaveInProgress());
		tag.putInt("waveTicks", this.getWaveTicks());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(TIERS, tag.getInt("tiers"));
		this.dataTracker.set(WAVES, tag.getInt("waves"));
		this.dataTracker.set(WAVEINPROGRESS, tag.getBoolean("waveInProgress"));
		this.dataTracker.set(WAVETICKS, tag.getInt("waveTicks"));
	}

	static {
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> WAVES =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> TIERS =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Boolean> WAVEINPROGRESS =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private static final TrackedData<Integer> WAVETICKS =
			DataTracker.registerData(GardenChallengeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private int getTierCount() {
		return this.dataTracker.get(TIERS);
	}

	public ChallengeTiers getTier() {
		return ChallengeTiers.byId(this.getTierCount() & 255);
	}

	public void setTier(ChallengeTiers variant) {
		this.dataTracker.set(TIERS, variant.getId() & 255);
	}

	private int getWaveCount() {
		return this.dataTracker.get(WAVES);
	}

	private void setWave(Integer count) {
		this.dataTracker.set(WAVES, count);
	}

	private void addWave(){
		int count = getWaveCount();
		this.dataTracker.set(WAVES, count + 1);
	}

	private int getWaveTicks() {
		return this.dataTracker.get(WAVETICKS);
	}

	private void setWaveticks(Integer count) {
		this.dataTracker.set(WAVETICKS, count);
	}


	public enum WaveInProgress {
		FALSE(false),
		TRUE(true);

		WaveInProgress(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getWaveInProgress() {
		return this.dataTracker.get(WAVEINPROGRESS);
	}

	public void setWaveinprogress(GardenChallengeEntity.WaveInProgress waveinprogress) {
		this.dataTracker.set(WAVEINPROGRESS, waveinprogress.getId());
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
		event.getController().setAnimation(new AnimationBuilder().loop("garden.idle"));
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				this.kill();
			}
		}
	}


	@Override
	public void onDeath(DamageSource source) {
		for (GraveEntity graveEntity : currentWorlds){
			graveEntity.discard();
		}
		super.onDeath(source);
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	int cooldown;

	public void tick() {
		checkEntities();
		waveManager();
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		--cooldown;
		if (getWaveInProgress()){
			this.setWaveticks(this.getWaveTicks() + 1);
		}
		else {
			this.setWaveticks(0);
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.kill();
		}
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.GARDEN_SPAWN.getDefaultStack();
	}

	protected List<GraveEntity> currentWorlds = new ArrayList<>();
	protected List<Entity> checkEntities = new ArrayList<>();

	public void checkEntities(){
		currentWorlds = this.world.getNonSpectatingEntities(GraveEntity.class, this.getBoundingBox());
		checkEntities = this.world.getNonSpectatingEntities(Entity.class, this.getBoundingBox().expand(20, 6, 20));
		Iterator var9 = checkEntities.iterator();
		while (true) {
			Entity entity;
			if (!var9.hasNext()) {
				return;
			}

			entity = (Entity) var9.next();

		}
	}

	public void waveManager(){
		if (this.getWaveInProgress() && this.getWaveTicks() >= 60){
			this.setWaveinprogress(WaveInProgress.FALSE);
		}
		if (this.getWaveInProgress() && this.world instanceof ServerWorld serverWorld){
			if (getWaveTicks() == 20 && this.getTier().equals(ChallengeTiers.ONE)){
				GraveEntity graveEntity = (GraveEntity) PvZEntity.BASICGRAVESTONE.create(this.world);
				BlockPos blockPos = new BlockPos(this.getBlockPos().getX() + 10, this.getBlockPos().getY(), this.getBlockPos().getZ() + 10);
				graveEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				graveEntity.initialize(serverWorld, this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				serverWorld.spawnEntityAndPassengers(graveEntity);
			}
			if (getWaveTicks() == 20 && this.getTier().equals(ChallengeTiers.TWO)){
				GraveEntity graveEntity = (GraveEntity) PvZEntity.NIGHTGRAVESTONE.create(this.world);
				BlockPos blockPos = new BlockPos(this.getBlockPos().getX() + 10, this.getBlockPos().getY(), this.getBlockPos().getZ() + 10);
				graveEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				graveEntity.initialize(serverWorld, this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				serverWorld.spawnEntityAndPassengers(graveEntity);
			}
		}
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (cooldown <= 0) {
			this.cooldown = 20;
			if (currentWorlds.isEmpty()) {
				this.addWorld(PvZEntity.BASICGRAVESTONE);
			}
			if (!currentWorlds.isEmpty()) {
				EntityType<?> entityType = PvZEntity.BASICGRAVESTONE;
				List<EntityType<?>> list = new ArrayList<>();
				list.add(PvZEntity.BASICGRAVESTONE);
				list.add(PvZEntity.NIGHTGRAVESTONE);
				list.add(PvZEntity.POOLGRAVESTONE);
				list.add(PvZEntity.ROOFGRAVESTONE);
				list.add(PvZEntity.EGYPTGRAVESTONE);
				list.add(PvZEntity.FUTUREGRAVESTONE);
				list.add(PvZEntity.DARKAGESGRAVESTONE);
				entityType = list.get(random.range(0, list.size() - 1));
				this.addWorld(entityType);
				this.setTier(ChallengeTiers.byId(this.getTierCount() + 1));
			}
		}
		return super.interactMob(player, hand);
	}

	public void addWorld(EntityType<?> entityType){
		if (this.world instanceof ServerWorld serverWorld) {
			GraveEntity graveEntity = (GraveEntity) entityType.create(this.world);
			BlockPos blockPos = this.getBlockPos();
			boolean bl = true;
			if (currentWorlds.isEmpty()) {
				blockPos = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
			}
			else if (currentWorlds.size() == 1) {
				blockPos = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ());
			}
			else if (currentWorlds.size() == 2) {
				blockPos = new BlockPos(this.getBlockPos().getX() + 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
			}
			else if (currentWorlds.size() == 3) {
				blockPos = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
			}
			else if (currentWorlds.size() == 4) {
				blockPos = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() - 1);
			}
			else if (currentWorlds.size() == 5) {
				blockPos = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ());
			}
			else if (currentWorlds.size() == 6) {
				blockPos = new BlockPos(this.getBlockPos().getX() - 1, this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
			}
			else if (currentWorlds.size() == 7) {
				blockPos = new BlockPos(this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ() + 1);
			}
			else {
				bl = false;
			}
			if (bl) {
				graveEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				graveEntity.initialize(serverWorld, this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				graveEntity.setAiDisabled(true);
				serverWorld.spawnEntityAndPassengers(graveEntity);
				this.addWave();
				this.setWaveinprogress(WaveInProgress.TRUE);
			}
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGardenAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 90.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {return PvZSounds.SILENCEVENET;}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZSounds.PLANTPLANTEDEVENT;}

	public boolean hurtByWater() {return false;}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {

	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}
}
