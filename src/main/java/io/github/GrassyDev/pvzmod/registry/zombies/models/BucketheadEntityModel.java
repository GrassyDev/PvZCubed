package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BucketheadEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BucketheadEntityModel extends AnimatedGeoModel<BucketheadEntity> {

    @Override
    public Identifier getModelLocation(BucketheadEntity object)
    {
        return new Identifier("pvzcubed", "geo/buckethead.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BucketheadEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/buckethead.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BucketheadEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}