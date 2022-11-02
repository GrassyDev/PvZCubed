package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FumeEntityModel extends AnimatedGeoModel<FumeEntity> {

    @Override
    public Identifier getModelResource(FumeEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(FumeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(FumeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
