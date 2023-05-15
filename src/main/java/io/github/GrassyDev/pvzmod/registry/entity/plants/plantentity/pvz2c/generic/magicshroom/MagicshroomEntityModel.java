package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MagicshroomEntityModel extends AnimatedGeoModel<MagicshroomEntity> {

    @Override
    public Identifier getModelResource(MagicshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/magicshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(MagicshroomEntity object)
    {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/magicshroom/magicshroom.png");
		if (!object.hasHat()){
			identifier = new Identifier("pvzmod", "textures/entity/magicshroom/magicshroom_hatless.png");
		}
        return identifier;
    }

    @Override
    public Identifier getAnimationResource(MagicshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/magicshroom.json");
    }
}
