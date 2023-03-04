package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gloomshroom;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GloomshroomEntityModel extends AnimatedGeoModel<GloomshroomEntity> {

    @Override
    public Identifier getModelResource(GloomshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/gloomshroom.geo.json");
    }

	public Identifier getTextureResource(GloomshroomEntity object) {
		return GloomshroomEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(GloomshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/gloomshroom.json");
    }
}
