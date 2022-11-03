package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HypnoGargantuarEntityModel extends AnimatedGeoModel<HypnoGargantuarEntity> {

    @Override
    public Identifier getModelResource(HypnoGargantuarEntity object)
    {
        return new Identifier("pvzmod", "geo/gargantuar.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoGargantuarEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gargantuar/gargantuar_hypnotized.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoGargantuarEntity object)
    {
        return new Identifier ("pvzmod", "animations/gargantuar.json");
    }
}
