package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb;

import net.minecraft.entity.LivingEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.entity.ai.goal.Goal;

public class CherryIgniteGoal extends Goal {
    private final CherrybombEntity cherry;
    private LivingEntity target;

    public CherryIgniteGoal(CherrybombEntity cherry) {
        this.cherry = cherry;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.cherry.getTarget();
        return this.cherry.getFuseSpeed() > 0 || livingEntity != null && this.cherry.squaredDistanceTo(livingEntity) < 144.0D;
    }

    public void start() {
        this.cherry.getNavigation().stop();
        this.target = this.cherry.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.cherry.setFuseSpeed(-1);
        } else if (this.cherry.squaredDistanceTo(this.target) > 144D || this.cherry.isInsideWaterOrBubbleColumn()) {
            this.cherry.setFuseSpeed(-1);
        } else {
            this.cherry.setFuseSpeed(1);
        }
    }
}
