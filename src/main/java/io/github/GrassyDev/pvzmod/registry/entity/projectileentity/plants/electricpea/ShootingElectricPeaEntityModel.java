package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.electricpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingElectricPeaEntityModel extends AnimatedGeoModel<ShootingElectricPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingElectricPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingElectricPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/electricpea.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingElectricPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
