package io.github.GrassyDev.pvzmod.registry.plants.plantentity.peashooter;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PeashooterEntityModel extends AnimatedGeoModel<PeashooterEntity> {

    @Override
    public Identifier getModelResource(PeashooterEntity object)
    {
        return new Identifier("pvzmod", "geo/peashooter.geo.json");
    }

    @Override
    public Identifier getTextureResource(PeashooterEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationResource(PeashooterEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
