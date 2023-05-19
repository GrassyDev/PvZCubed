package io.github.GrassyDev.pvzmod.registry.entity.environment.solarwinds;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SolarWindsModel extends AnimatedGeoModel<SolarWinds> {

    @Override
    public Identifier getModelResource(SolarWinds object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(SolarWinds object)
    {
		return new Identifier("pvzmod", "textures/entity/projectiles/peashot.png");
    }

    @Override
    public Identifier getAnimationResource(SolarWinds object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
