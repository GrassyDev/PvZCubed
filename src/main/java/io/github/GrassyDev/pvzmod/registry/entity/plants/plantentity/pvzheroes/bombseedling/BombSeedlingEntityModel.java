package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BombSeedlingEntityModel extends AnimatedGeoModel<BombSeedlingEntity> {

    @Override
    public Identifier getModelResource(BombSeedlingEntity object)
    {
        return new Identifier("pvzmod", "geo/bombseedling.geo.json");
    }

    @Override
    public Identifier getTextureResource(BombSeedlingEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/small/bombseedling.png");
    }

    @Override
    public Identifier getAnimationResource(BombSeedlingEntity object)
    {
        return new Identifier ("pvzmod", "animations/small.json");
    }
}
