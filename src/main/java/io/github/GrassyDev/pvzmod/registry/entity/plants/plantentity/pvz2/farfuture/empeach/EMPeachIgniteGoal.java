package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.farfuture.empeach;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class EMPeachIgniteGoal extends Goal {
    private final EMPeachEntity emPeachEntity;
    private LivingEntity target;

    public EMPeachIgniteGoal(EMPeachEntity bombSeedling) {
        this.emPeachEntity = bombSeedling;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.emPeachEntity.getTarget();
        return this.emPeachEntity.getFuseSpeed() > 0 || livingEntity != null && this.emPeachEntity.squaredDistanceTo(livingEntity) < 144.0D;
    }

    public void start() {
        this.emPeachEntity.getNavigation().stop();
        this.target = this.emPeachEntity.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.emPeachEntity.setFuseSpeed(-1);
        } else if (this.emPeachEntity.squaredDistanceTo(this.target) > 144.0D || this.emPeachEntity.isInsideWaterOrBubbleColumn()) {
            this.emPeachEntity.setFuseSpeed(-1);
        } else {
            this.emPeachEntity.setFuseSpeed(1);
        }
    }
}
