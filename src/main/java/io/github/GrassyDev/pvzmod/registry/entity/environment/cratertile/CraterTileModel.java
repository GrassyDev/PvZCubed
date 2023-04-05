package io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CraterTileModel extends AnimatedGeoModel<CraterTile> {

    @Override
    public Identifier getModelResource(CraterTile object)
    {
        return new Identifier("pvzmod", "geo/cratertile.geo.json");
    }

    @Override
    public Identifier getTextureResource(CraterTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(CraterTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
