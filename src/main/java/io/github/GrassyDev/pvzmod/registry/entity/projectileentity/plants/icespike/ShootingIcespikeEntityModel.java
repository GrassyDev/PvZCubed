package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingIcespikeEntityModel extends AnimatedGeoModel<ShootingIcespikeEntity> {

    @Override
    public Identifier getModelResource(ShootingIcespikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingIcespikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/spikeice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingIcespikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
