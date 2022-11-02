package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.doomshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DoomshroomEntityModel extends AnimatedGeoModel<DoomshroomEntity> {

    @Override
    public Identifier getModelResource(DoomshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/doomshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(DoomshroomEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/doomshroom/doomshroom.png");
    }

    @Override
    public Identifier getAnimationResource(DoomshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/doomshroom.json");
    }
}
