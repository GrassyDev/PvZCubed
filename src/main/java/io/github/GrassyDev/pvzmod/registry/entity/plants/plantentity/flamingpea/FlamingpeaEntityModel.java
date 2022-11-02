package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.flamingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlamingpeaEntityModel extends AnimatedGeoModel<FlamingpeaEntity> {

    @Override
    public Identifier getModelResource(FlamingpeaEntity object)
    {
        return new Identifier("pvzmod", "geo/flamingpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlamingpeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/flamingpea.png");
    }

    @Override
    public Identifier getAnimationResource(FlamingpeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
