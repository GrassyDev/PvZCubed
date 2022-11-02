package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.FumeEntityVariants;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FumeEntityModel_G extends AnimatedGeoModel<FumeEntity_G> {

    @Override
    public Identifier getModelResource(FumeEntity_G object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(FumeEntity_G object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(FumeEntity_G object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
