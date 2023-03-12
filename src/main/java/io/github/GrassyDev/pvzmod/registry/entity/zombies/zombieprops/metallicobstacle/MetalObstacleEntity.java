package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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

public class MetalObstacleEntity extends ZombieObstacleEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public MetalObstacleEntity(EntityType<? extends MetalObstacleEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
		this.colliderOffset = 0;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	public MetalObstacleEntity(World world) {
		this(PvZEntity.BASKETBALLBIN, world);
	}

	static {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2){
			super.handleStatus(status);
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		if (this.hasVehicle() && this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHealth() <= 0 || generalPvZombieEntity.isDead())){
			this.dismountVehicle();
		}
		if (this.CollidesWithPlant() != null){
			if (this.CollidesWithPlant() instanceof SpikerockEntity) {
				if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
					this.CollidesWithPlant().damage(DamageSource.thrownProjectile(this, this), 90);
					this.remove(RemovalReason.KILLED);
				}
				else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
					this.CollidesWithPlant().damage(DamageSource.thrownProjectile(this, this), 90);
					this.remove(RemovalReason.KILLED);
				}
			}
			else if (this.CollidesWithPlant() instanceof SpikeweedEntity) {
				if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
					this.CollidesWithPlant().kill();
					this.remove(RemovalReason.KILLED);
				}
				else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
					this.CollidesWithPlant().kill();
					this.remove(RemovalReason.KILLED);
				}
			}
			else if (this.CollidesWithPlant() != null && !(this.CollidesWithPlant() instanceof GravebusterEntity)) {
				if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()){
					this.CollidesWithPlant().kill();
				}
				else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
					this.CollidesWithPlant().kill();
				}
			}
		}
		super.tick();
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
		if (beingEaten || (this.getType().equals(PvZEntity.TRASHCANBIN) && (this.hasVehicle() || (this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHealth() <= 0)))){
			event.getController().setAnimation(new AnimationBuilder().loop("obstacle.eating"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("gravestone.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createBasketBallBinObstacleAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 120D);
    }

	public static DefaultAttributeContainer.Builder createTrashCanBinObstacleAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 225D);
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ITEM_SHIELD_BREAK;
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZCubed.SILENCEVENET;
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getType().equals(PvZEntity.BASKETBALLBIN)){
			itemStack = ModItems.BASKETBALLCARRIEREGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.TRASHCANBIN)){
			itemStack = ModItems.TRASHCANEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.BASKETBALLCARRIEREGG.getDefaultStack();
		}
		return itemStack;
	}
}
