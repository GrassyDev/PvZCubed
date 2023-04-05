package io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScorchedTileModel extends AnimatedGeoModel<ScorchedTile> {

    @Override
    public Identifier getModelResource(ScorchedTile object)
    {
        return new Identifier("pvzmod", "geo/scorchedtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScorchedTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(ScorchedTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
