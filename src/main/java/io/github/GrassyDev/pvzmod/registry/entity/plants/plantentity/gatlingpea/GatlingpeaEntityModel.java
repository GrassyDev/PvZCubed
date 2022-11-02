package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gatlingpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GatlingpeaEntityModel extends AnimatedGeoModel<GatlingpeaEntity> {

    @Override
    public Identifier getModelResource(GatlingpeaEntity object)
    {
        return new Identifier("pvzmod", "geo/gatlingpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(GatlingpeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/gatlingpea.png");
    }

    @Override
    public Identifier getAnimationResource(GatlingpeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
