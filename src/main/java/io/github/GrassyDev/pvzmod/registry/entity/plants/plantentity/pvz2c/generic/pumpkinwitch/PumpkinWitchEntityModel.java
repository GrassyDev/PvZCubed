package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.pumpkinwitch;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PumpkinWitchEntityModel extends AnimatedGeoModel<PumpkinWitchEntity> {

    @Override
    public Identifier getModelResource(PumpkinWitchEntity object)
    {
        return new Identifier("pvzmod", "geo/pumpkinwitch.geo.json");
    }

    @Override
    public Identifier getTextureResource(PumpkinWitchEntity object)
    {
		if (!object.hasProj()){
			return new Identifier("pvzmod", "textures/entity/pumpkinwitch/pumpkinwitch_projless.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/pumpkinwitch/pumpkinwitch.png");
		}
    }

    @Override
    public Identifier getAnimationResource(PumpkinWitchEntity object)
    {
        return new Identifier ("pvzmod", "animations/pumpkinwitch.json");
    }
}
