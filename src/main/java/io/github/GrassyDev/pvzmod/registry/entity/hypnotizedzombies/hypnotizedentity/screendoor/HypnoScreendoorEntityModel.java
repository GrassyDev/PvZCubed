package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoScreendoorEntityModel extends AnimatedGeoModel<HypnoScreendoorEntity> {

    @Override
    public Identifier getModelResource(HypnoScreendoorEntity object)
    {
        return new Identifier("pvzmod", "geo/screendoor.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoScreendoorEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/screendoor_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoScreendoorEntity object)
    {
        return new Identifier ("pvzmod", "animations/screendoor.json");
    }
}
