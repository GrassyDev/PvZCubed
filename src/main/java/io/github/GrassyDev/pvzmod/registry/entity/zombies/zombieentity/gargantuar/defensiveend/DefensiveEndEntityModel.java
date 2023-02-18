package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.defensiveend;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DefensiveEndEntityModel extends AnimatedGeoModel<DefensiveEndEntity> {

    @Override
    public Identifier getModelResource(DefensiveEndEntity object)
    {
        return new Identifier("pvzmod", "geo/defensiveend.geo.json");
    }

    @Override
    public Identifier getTextureResource(DefensiveEndEntity object)
    {
		return DefensiveEndEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(DefensiveEndEntity object)
    {
        return new Identifier ("pvzmod", "animations/gargantuar.json");
    }
}
