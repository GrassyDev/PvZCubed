package io.github.GrassyDev.pvzmod.registry.entity.environment.icetile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IceTileModel extends AnimatedGeoModel<IceTile> {

    @Override
    public Identifier getModelResource(IceTile object)
    {
        return new Identifier("pvzmod", "geo/icetile.geo.json");
    }

    @Override
    public Identifier getTextureResource(IceTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(IceTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
