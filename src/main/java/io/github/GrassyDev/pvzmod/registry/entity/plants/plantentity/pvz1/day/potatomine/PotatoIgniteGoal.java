package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;

public class PotatoIgniteGoal extends Goal {
    private final PotatomineEntity potato;
    private LivingEntity target;

    public PotatoIgniteGoal(PotatomineEntity potato) {
        this.potato = potato;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.potato.getTarget();
        return this.potato.getFuseSpeed() > 0 || livingEntity != null && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
				generalPvZombieEntity.isFlying()) && this.potato.squaredDistanceTo(livingEntity) <= Math.pow(this.potato.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE), 2);
    }

    public void start() {
        this.potato.getNavigation().stop();
        this.target = this.potato.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.potato.setFuseSpeed(-1);
        } else if (!this.target.isAlive()){
            this.potato.setFuseSpeed(-1);
        } else if (this.potato.squaredDistanceTo(this.target) > Math.pow(this.potato.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE), 2) || this.potato.isInsideWaterOrBubbleColumn()) {
            this.potato.setFuseSpeed(-1);
        } else {
            this.potato.setFuseSpeed(1);
        }
    }
}
