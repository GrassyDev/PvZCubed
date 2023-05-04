package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.beautyshroom;

import net.minecraft.entity.LivingEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.entity.ai.goal.Goal;

public class BeautyIgniteGoal extends Goal {
    private final BeautyshroomEntity beautyshroom;
    private LivingEntity target;

    public BeautyIgniteGoal(BeautyshroomEntity beautyshroom) {
        this.beautyshroom = beautyshroom;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.beautyshroom.getTarget();
        return (this.beautyshroom.getFuseSpeed() > 0 || livingEntity != null && this.beautyshroom.squaredDistanceTo(livingEntity) < 25.0D) && !this.beautyshroom.getIsAsleep();
    }

    public void start() {
        this.beautyshroom.getNavigation().stop();
        this.target = this.beautyshroom.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
		if (!beautyshroom.getIsAsleep()) {
			if (this.target == null) {
				this.beautyshroom.setFuseSpeed(-1);
			} else if (this.beautyshroom.squaredDistanceTo(this.target) > 25.0D || this.beautyshroom.isInsideWaterOrBubbleColumn()) {
				this.beautyshroom.setFuseSpeed(-1);
			} else {
				this.beautyshroom.setFuseSpeed(1);
			}
		}
		else {
			this.beautyshroom.setFuseSpeed(-1);
		}
    }
}
