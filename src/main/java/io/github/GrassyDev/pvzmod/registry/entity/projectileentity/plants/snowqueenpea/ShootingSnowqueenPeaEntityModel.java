package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea;

import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea.ShootingSnowPeaEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingSnowqueenPeaEntityModel extends AnimatedGeoModel<ShootingSnowqueenPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingSnowqueenPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingSnowqueenPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/spikeice.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingSnowqueenPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
