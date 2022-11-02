package io.github.GrassyDev.pvzmod.registry.entity.zombies.models;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.BucketheadEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BucketheadEntityModel extends AnimatedGeoModel<BucketheadEntity> {

    @Override
    public Identifier getModelResource(BucketheadEntity object)
    {
        return new Identifier("pvzmod", "geo/buckethead.geo.json");
    }

    @Override
    public Identifier getTextureResource(BucketheadEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/buckethead.png");
    }

    @Override
    public Identifier getAnimationResource(BucketheadEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
