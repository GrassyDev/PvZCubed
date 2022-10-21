package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingSnowPeaEntityModel extends AnimatedGeoModel<ShootingSnowPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingSnowPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peaice.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSnowPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/peaice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSnowPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
