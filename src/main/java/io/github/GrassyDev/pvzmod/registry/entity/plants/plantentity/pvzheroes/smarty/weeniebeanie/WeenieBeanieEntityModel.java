package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.weeniebeanie;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WeenieBeanieEntityModel extends AnimatedGeoModel<WeenieBeanieEntity> {

    @Override
    public Identifier getModelResource(WeenieBeanieEntity object)
    {
        return new Identifier("pvzmod", "geo/weeniebeanie.geo.json");
    }

    @Override
    public Identifier getTextureResource(WeenieBeanieEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/small/weeniebeanie.png");
    }

    @Override
    public Identifier getAnimationResource(WeenieBeanieEntity object)
    {
        return new Identifier ("pvzmod", "animations/navybean.json");
    }
}
