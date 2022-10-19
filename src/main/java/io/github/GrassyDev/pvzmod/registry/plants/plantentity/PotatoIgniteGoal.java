package net.fabricmc.example.registry.plants.plantentity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class PotatoIgniteGoal extends Goal {
    private final PotatomineEntity potato;
    private LivingEntity target;

    public PotatoIgniteGoal(PotatomineEntity potato) {
        this.potato = potato;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.potato.getTarget();
        return this.potato.getFuseSpeed() > 0 || livingEntity != null && this.potato.squaredDistanceTo(livingEntity) < 3.0D;
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
        } else if (this.potato.squaredDistanceTo(this.target) > 3.0D || this.potato.isInsideWaterOrBubbleColumn()) {
            this.potato.setFuseSpeed(-1);
        } else {
            this.potato.setFuseSpeed(1);
        }
    }
}
