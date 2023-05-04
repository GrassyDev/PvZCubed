package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan;

import net.minecraft.entity.LivingEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.entity.ai.goal.Goal;

public class ImpIgniteGoal extends Goal {
    private final SuperFanImpEntity imp;
    private LivingEntity target;

    public ImpIgniteGoal(SuperFanImpEntity imp) {
        this.imp = imp;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.imp.getTarget();
        return this.imp.getFuseSpeed() > 0 || livingEntity != null && this.imp.squaredDistanceTo(livingEntity) < 4.0D;
    }

    public void start() {
        this.imp.getNavigation().stop();
        this.target = this.imp.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.imp.setFuseSpeed(-1);
        } else if (this.imp.squaredDistanceTo(this.target) > 4.0D || this.imp.isInsideWaterOrBubbleColumn()) {
            this.imp.setFuseSpeed(-1);
        } else {
            this.imp.setFuseSpeed(1);
        }
    }
}
