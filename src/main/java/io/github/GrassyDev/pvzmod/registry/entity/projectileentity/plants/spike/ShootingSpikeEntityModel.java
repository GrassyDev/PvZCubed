package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingSpikeEntityModel extends AnimatedGeoModel<ShootingSpikeEntity> {

    @Override
    public Identifier getModelResource(ShootingSpikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSpikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/spike.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSpikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
