package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spit;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpitEntityModel extends AnimatedGeoModel<SpitEntity> {

    @Override
    public Identifier getModelResource(SpitEntity object)
    {
        return new Identifier("pvzmod", "geo/spit.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpitEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/spit.png");
    }

    @Override
    public Identifier getAnimationResource(SpitEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
