package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.entity.LivingEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.entity.ai.goal.Goal;

public class IcebergIgniteGoal extends Goal {
    private final IcebergLettuceEntity icebergLettuce;
    private LivingEntity target;

    public IcebergIgniteGoal(IcebergLettuceEntity icebergLettuce) {
        this.icebergLettuce = icebergLettuce;
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.icebergLettuce.getTarget();
        return this.icebergLettuce.getFuseSpeed() > 0 || livingEntity != null && (!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
				generalPvZombieEntity.isFlying())) && this.icebergLettuce.squaredDistanceTo(livingEntity) < 4.0D;
    }

    public void start() {
        this.icebergLettuce.getNavigation().stop();
        this.target = this.icebergLettuce.getTarget();
    }

    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.icebergLettuce.setFuseSpeed(-1);
        } else if (this.icebergLettuce.squaredDistanceTo(this.target) > 4.0D || this.icebergLettuce.isInsideWaterOrBubbleColumn()) {
            this.icebergLettuce.setFuseSpeed(-1);
        } else {
            this.icebergLettuce.setFuseSpeed(1);
        }
    }
}
