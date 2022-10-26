package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import com.google.common.collect.ImmutableList;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.plants.planttypes.ReinforceEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.*;
import java.util.stream.Stream;

;

public class WallnutEntity extends ReinforceEntity implements IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);
    private String controllerName = "wallcontroller";
    protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;

    public int healingTime;

    public int damageTaken;

    public WallnutEntity(EntityType<? extends WallnutEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 100;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.damageTaken == 3){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("wallnut.damage3", true));
        }
        else if (this.damageTaken == 2){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("wallnut.damage2", true));
        }
        else if (this.damageTaken == 1){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("wallnut.damage1", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("wallnut.idle", true));
        }
        return PlayState.CONTINUE;
    }

	public boolean isCollidable() {
		return this.isAlive();
	}


    public void calculateDimensions() {
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, e, f);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
    }

    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        if (tag.contains("APX")) {
            int i = tag.getInt("APX");
            int j = tag.getInt("APY");
            int k = tag.getInt("APZ");
            this.dataTracker.set(ATTACHED_BLOCK, Optional.of(new BlockPos(i, j, k)));
        } else {
            this.dataTracker.set(ATTACHED_BLOCK, Optional.empty());
        }
    }

    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        BlockPos blockPos = this.getAttachedBlock();
        if (blockPos != null) {
            tag.putInt("APX", blockPos.getX());
            tag.putInt("APY", blockPos.getY());
            tag.putInt("APZ", blockPos.getZ());
        }
    }

    public void tick() {
        super.tick();
        BlockPos blockPos = (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
        if (blockPos == null && !this.world.isClient) {
            blockPos = this.getBlockPos();
            this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
        }

        if (blockPos != null) {
            this.setPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
        }

        WallnutEntity.Crack crack = this.getCrack();
        if (crack == Crack.HIGH) {
            this.world.sendEntityStatus(this, (byte)6);
        }
        else if (crack == Crack.MEDIUM) {
            this.world.sendEntityStatus(this, (byte)5);
        }
        else if (crack == Crack.LOW) {
            this.world.sendEntityStatus(this, (byte)4);
        }
        else if (crack == Crack.NONE) {
            this.world.sendEntityStatus(this, (byte)3);
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            this.damageTaken = 0;
        }
        if (status == 4) {
            this.damageTaken = 1;
        }
        if (status == 5) {
            this.damageTaken = 2;
        }
        if (status == 6) {
            this.damageTaken = 3;
        }
    }

    public void updatePosition(double x, double y, double z) {
        super.updatePosition(x, y, z);
        if (this.dataTracker != null && this.age != 0) {
            Optional<BlockPos> optional = (Optional)this.dataTracker.get(ATTACHED_BLOCK);
            Optional<BlockPos> optional2 = Optional.of(new BlockPos(x, y, z));
            if (!optional2.equals(optional)) {
                this.dataTracker.set(ATTACHED_BLOCK, optional2);
                this.velocityDirty = true;
            }

        }
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (ATTACHED_BLOCK.equals(data) && this.world.isClient && !this.hasVehicle()) {
            BlockPos blockPos = this.getAttachedBlock();
            if (blockPos != null) {
				this.setPosition((double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D);
            }
        }

        super.onTrackedDataSet(data);
    }

    @Nullable
    public BlockPos getAttachedBlock() {
        return (BlockPos)((Optional)this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object)null);
    }

    static {
        ATTACHED_BLOCK = DataTracker.registerData(WallnutEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
    }

    public void move(MovementType type, Vec3d movement) {
        if (type == MovementType.SHULKER_BOX) {
            this.damage(DamageSource.GENERIC, 9999);
        } else {
            super.move(type, movement);
        }

    }

    public boolean collides() {
        return true;
    }

    public boolean handleAttack(Entity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)attacker;
            return this.damage(DamageSource.player(playerEntity), 9999.0F);
        } else {
            return false;
        }
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        if (fallDistance > 0F) {
            this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
            this.damage(DamageSource.GENERIC, 9999);
        }
        this.playBlockFallSound();
        return true;
    }

    protected boolean canClimb() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    protected void initGoals() {
        this.goalSelector.add(1, new LookAtEntityGoal(this, HostileEntity.class, 50.0F));
    }

    public static DefaultAttributeContainer.Builder createWallnutAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 180D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 && !this.isInsideWaterOrBubbleColumn() &&  this.deathTime == 0) {
            this.heal(1.0F);
            this.healingTime = 100;
        }

        if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
            this.damage(DamageSource.GENERIC, 9999);
        }
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.60F;
    }

    public boolean damage(DamageSource source, float amount) {
        WallnutEntity.Crack crack = this.getCrack();
        boolean bl = super.damage(source, amount);
        if (bl && this.getCrack() != crack) {
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
        }

        return bl;
    }

    public WallnutEntity.Crack getCrack() {
        return WallnutEntity.Crack.from(this.getHealth() / this.getMaxHealth());
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
    protected SoundEvent getHurtSound(DamageSource source) {
        return PvZCubed.ZOMBIEBITEEVENT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return PvZCubed.PLANTPLANTEDEVENT;
    }

    public static boolean canWallnutSpawn(EntityType<WallnutEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 60;
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }

    @Environment(EnvType.CLIENT)
    public Vec3d method_29919() {
        return new Vec3d(0.0D, (double) (0.75F * this.getStandingEyeHeight()), (double) (this.getWidth() * 0.4F));
    }

    public static enum Crack {
        NONE(1.0F),
        LOW(0.75F),
        MEDIUM(0.5F),
        HIGH(0.25F);

        private static final List<WallnutEntity.Crack> VALUES = (List) Stream.of(values()).sorted(Comparator.comparingDouble((crack) -> {
            return (double)crack.maxHealthFraction;
        })).collect(ImmutableList.toImmutableList());
        private final float maxHealthFraction;

        private Crack(float maxHealthFraction) {
            this.maxHealthFraction = maxHealthFraction;
        }

        public static WallnutEntity.Crack from(float healthFraction) {
            Iterator var1 = VALUES.iterator();

            WallnutEntity.Crack crack;
            do {
                if (!var1.hasNext()) {
                    return NONE;
                }

                crack = (WallnutEntity.Crack)var1.next();
            } while(!(healthFraction < crack.maxHealthFraction));

            return crack;
        }
    }
}
