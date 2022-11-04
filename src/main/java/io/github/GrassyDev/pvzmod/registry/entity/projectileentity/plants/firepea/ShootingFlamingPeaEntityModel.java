package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.firepea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingFlamingPeaEntityModel extends AnimatedGeoModel<ShootingFlamingPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingFlamingPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingFlamingPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/firepea.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingFlamingPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
