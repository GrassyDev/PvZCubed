package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.imp.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoImpEntityModel extends AnimatedGeoModel<HypnoImpEntity> {

    @Override
    public Identifier getModelResource(HypnoImpEntity object)
    {
        return new Identifier("pvzmod", "geo/imp.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoImpEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/imp/imp_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
