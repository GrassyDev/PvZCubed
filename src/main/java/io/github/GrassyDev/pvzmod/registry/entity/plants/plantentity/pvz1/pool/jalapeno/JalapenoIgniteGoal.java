package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import net.minecraft.entity.LivingEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.entity.ai.goal.Goal;

public class JalapenoIgniteGoal extends Goal {
    private final JalapenoEntity pepper;
    private LivingEntity target;

    public JalapenoIgniteGoal(JalapenoEntity pepper) {
        this.pepper = pepper;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.pepper.getTarget();
        return this.pepper.getFuseSpeed() > 0 || livingEntity != null && this.pepper.squaredDistanceTo(livingEntity) < 144.0D && !pepper.isWet();
    }

    public void start() {
        this.pepper.getNavigation().stop();
        this.target = this.pepper.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
		if (!pepper.isWet()) {
			if (this.target == null) {
				this.pepper.setFuseSpeed(-1);
			} else if (this.pepper.squaredDistanceTo(this.target) > 144D || this.pepper.isInsideWaterOrBubbleColumn()) {
				this.pepper.setFuseSpeed(-1);
			} else {
				this.pepper.setFuseSpeed(1);
			}
		}
    }
}
