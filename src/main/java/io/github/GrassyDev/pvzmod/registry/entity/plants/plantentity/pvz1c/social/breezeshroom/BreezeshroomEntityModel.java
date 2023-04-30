package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.breezeshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BreezeshroomEntityModel extends AnimatedGeoModel<BreezeshroomEntity> {

    @Override
    public Identifier getModelResource(BreezeshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/breezeshroom.geo.json");
    }

	public Identifier getTextureResource(BreezeshroomEntity object) {
		return new Identifier("pvzmod", "textures/entity/fumeshroom/breezeshroom.png");
	}

    @Override
    public Identifier getAnimationResource(BreezeshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/fumeshroom.json");
    }
}
