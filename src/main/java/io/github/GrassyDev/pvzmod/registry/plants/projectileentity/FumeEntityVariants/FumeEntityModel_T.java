package io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntityVariants;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FumeEntityModel_T extends AnimatedGeoModel<FumeEntity_T> {

    @Override
    public Identifier getModelResource(FumeEntity_T object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(FumeEntity_T object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(FumeEntity_T object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
