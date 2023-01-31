package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FumeshroomEntityModel extends AnimatedGeoModel<FumeshroomEntity> {

    @Override
    public Identifier getModelResource(FumeshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/fumeshroom.geo.json");
    }

	public Identifier getTextureResource(FumeshroomEntity object) {
		return FumeshroomEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(FumeshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/fumeshroom.json");
    }
}
