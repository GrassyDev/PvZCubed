package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.acidfume;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AcidFumeEntityModel extends AnimatedGeoModel<AcidFumeEntity> {

    @Override
    public Identifier getModelResource(AcidFumeEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(AcidFumeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(AcidFumeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
