package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class IceIgniteGoal extends Goal {
    private final IceshroomEntity ice;
    private LivingEntity target;

    public IceIgniteGoal(IceshroomEntity ice) {
        this.ice = ice;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.ice.getTarget();
        return (this.ice.getFuseSpeed() > 0 || livingEntity != null && this.ice.squaredDistanceTo(livingEntity) < 25.0D) && !this.ice.isAsleep;
    }

    public void start() {
        this.ice.getNavigation().stop();
        this.target = this.ice.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.ice.setFuseSpeed(-1);
        } else if (this.ice.squaredDistanceTo(this.target) > 25.0D || this.ice.isInsideWaterOrBubbleColumn()) {
            this.ice.setFuseSpeed(-1);
        } else {
            this.ice.setFuseSpeed(1);
        }
    }
}
