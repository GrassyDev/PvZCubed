package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.retrogatling;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RetroGatlingEntityModel extends AnimatedGeoModel<RetroGatlingEntity> {

    @Override
    public Identifier getModelResource(RetroGatlingEntity object)
    {
        return new Identifier("pvzmod", "geo/retrogatling.geo.json");
    }

    @Override
    public Identifier getTextureResource(RetroGatlingEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/retrogatling.png");
    }

    @Override
    public Identifier getAnimationResource(RetroGatlingEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
