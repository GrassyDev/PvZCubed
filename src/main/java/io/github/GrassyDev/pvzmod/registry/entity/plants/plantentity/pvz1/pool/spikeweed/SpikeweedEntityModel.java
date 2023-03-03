package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpikeweedEntityModel extends AnimatedGeoModel<SpikeweedEntity> {

    @Override
    public Identifier getModelResource(SpikeweedEntity object)
    {
        return new Identifier("pvzmod", "geo/spikeweed.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpikeweedEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/spikeweed/spikeweed.png");
    }

    @Override
    public Identifier getAnimationResource(SpikeweedEntity object)
    {
        return new Identifier ("pvzmod", "animations/spikeweed.json");
    }
}
