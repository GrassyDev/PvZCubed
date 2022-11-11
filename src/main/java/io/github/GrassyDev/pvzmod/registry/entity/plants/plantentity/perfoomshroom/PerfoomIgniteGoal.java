package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.perfoomshroom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class PerfoomIgniteGoal extends Goal {
    private final PerfoomshroomEntity doom;
    private LivingEntity target;

    public PerfoomIgniteGoal(PerfoomshroomEntity doom) {
        this.doom = doom;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.doom.getTarget();
        return (this.doom.getFuseSpeed() > 0 || livingEntity != null && this.doom.squaredDistanceTo(livingEntity) < 25.0D) && !this.doom.isTired;
    }

    public void start() {
        this.doom.getNavigation().stop();
        this.target = this.doom.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.doom.setFuseSpeed(-1);
        } else if (this.doom.squaredDistanceTo(this.target) > 25.0D || this.doom.isInsideWaterOrBubbleColumn()) {
            this.doom.setFuseSpeed(-1);
        } else {
            this.doom.setFuseSpeed(1);
        }
    }
}
