package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TorchwoodEntityModel extends AnimatedGeoModel<TorchwoodEntity> {

    @Override
    public Identifier getModelResource(TorchwoodEntity object)
    {
        return new Identifier("pvzmod", "geo/torchwood.geo.json");
    }

    @Override
    public Identifier getTextureResource(TorchwoodEntity object)
    {
		return object.isWet()? new Identifier ("pvzmod", "textures/entity/torchwood/torchwood_extinguished.png") :
				new Identifier ("pvzmod", "textures/entity/torchwood/torchwood.png");
    }

    @Override
    public Identifier getAnimationResource(TorchwoodEntity object)
    {
        return new Identifier ("pvzmod", "animations/torchwood.json");
    }
}
