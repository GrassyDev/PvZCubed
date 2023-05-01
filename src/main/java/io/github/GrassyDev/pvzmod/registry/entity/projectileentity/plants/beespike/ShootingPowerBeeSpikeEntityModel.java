package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.beespike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShootingPowerBeeSpikeEntityModel extends AnimatedGeoModel<ShootingPowerBeeSpikeEntity> {

    @Override
    public Identifier getModelResource(ShootingPowerBeeSpikeEntity object)
    {
        return new Identifier("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPowerBeeSpikeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/powerbeespike.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPowerBeeSpikeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
