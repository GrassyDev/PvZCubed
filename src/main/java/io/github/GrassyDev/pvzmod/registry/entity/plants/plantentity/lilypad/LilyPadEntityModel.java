package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.wallnutentity.WallnutEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LilyPadEntityModel extends AnimatedGeoModel<LilyPadEntity> {

    @Override
    public Identifier getModelResource(LilyPadEntity object)
    {
        return new Identifier("pvzmod", "geo/lilypad.geo.json");
    }

    @Override
    public Identifier getTextureResource(LilyPadEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/lilypad.png");
    }

    @Override
    public Identifier getAnimationResource(LilyPadEntity object)
    {
        return new Identifier ("pvzmod", "animations/lilypad.json");
    }
}
