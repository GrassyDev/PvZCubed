package io.github.GrassyDev.pvzmod.registry.entity.environment.goldtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoldTileModel extends AnimatedGeoModel<GoldTile> {

    @Override
    public Identifier getModelResource(GoldTile object)
    {
        return new Identifier("pvzmod", "geo/goldtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(GoldTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(GoldTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
