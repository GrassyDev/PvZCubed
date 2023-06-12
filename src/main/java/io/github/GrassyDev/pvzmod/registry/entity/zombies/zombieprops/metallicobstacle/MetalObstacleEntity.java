package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MetalObstacleEntity extends ZombieObstacleEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public MetalObstacleEntity(EntityType<? extends MetalObstacleEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
	}

	public MetalObstacleEntity(World world) {
		this(PvZEntity.BASKETBALLBIN, world);
	}

	static {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		if (this.hasVehicle() && this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHealth() <= 0 || generalPvZombieEntity.isDead())){
			this.dismountVehicle();
		}
		if (this.CollidesWithPlant(0f, 0f) != null){
			if (this.CollidesWithPlant(0f, 0f) instanceof SpikerockEntity) {
				if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
					this.CollidesWithPlant(0f, 0f).damage(DamageSource.thrownProjectile(this, this), 90);
					this.kill();
				}
				else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
					this.CollidesWithPlant(0f, 0f).damage(DamageSource.thrownProjectile(this, this), 90);
					this.kill();
				}
			}
			else if (this.CollidesWithPlant(0f, 0f) instanceof SpikeweedEntity) {
				if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
					this.CollidesWithPlant(0f, 0f).kill();
					this.kill();
				}
				else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
					this.CollidesWithPlant(0f, 0f).kill();
					this.kill();
				}
			}
			else if (this.CollidesWithPlant(0f, 0f) != null && !(this.CollidesWithPlant(0f, 0f) instanceof GravebusterEntity)) {
				if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()){
					this.CollidesWithPlant(0f, 0f).kill();
				}
				else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
					this.CollidesWithPlant(0f, 0f).kill();
				}
			}
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.basketballObstH());
    }

	public static DefaultAttributeContainer.Builder createTrashCanBinObstacleAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.trashcanObstH());
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ITEM_SHIELD_BREAK;
	}

	protected SoundEvent getAmbientSound() {
		return PvZSounds.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
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
