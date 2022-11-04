package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingPlasmaPeaEntityModel extends AnimatedGeoModel<ShootingPlasmaPeaEntity> {

    @Override
    public Identifier getModelResource(ShootingPlasmaPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPlasmaPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/plasmapea.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPlasmaPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
