package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingPowerIcespikeEntityModel extends AnimatedGeoModel<ShootingPowerIcespikeEntity> {

    @Override
    public Identifier getModelResource(ShootingPowerIcespikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPowerIcespikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/powerspikeice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPowerIcespikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
