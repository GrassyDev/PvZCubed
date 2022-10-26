package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingPeaEntityModel extends AnimatedGeoModel<ShootingPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
