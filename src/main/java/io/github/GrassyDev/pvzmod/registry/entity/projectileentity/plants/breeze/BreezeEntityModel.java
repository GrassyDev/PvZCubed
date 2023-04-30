package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.breeze;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BreezeEntityModel extends AnimatedGeoModel<BreezeEntity> {

    @Override
    public Identifier getModelResource(BreezeEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(BreezeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(BreezeEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
