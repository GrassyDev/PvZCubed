package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class BombSeedlingIgniteGoal extends Goal {
    private final BombSeedlingEntity bombSeedling;
    private LivingEntity target;

    public BombSeedlingIgniteGoal(BombSeedlingEntity bombSeedling) {
        this.bombSeedling = bombSeedling;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.bombSeedling.getTarget();
        return this.bombSeedling.getFuseSpeed() > 0 || livingEntity != null && this.bombSeedling.squaredDistanceTo(livingEntity) < 4.0D;
    }

    public void start() {
        this.bombSeedling.getNavigation().stop();
        this.target = this.bombSeedling.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.bombSeedling.setFuseSpeed(-1);
        } else if (this.bombSeedling.squaredDistanceTo(this.target) > 4.0D || this.bombSeedling.isInsideWaterOrBubbleColumn()) {
            this.bombSeedling.setFuseSpeed(-1);
        } else {
            this.bombSeedling.setFuseSpeed(1);
        }
    }
}
