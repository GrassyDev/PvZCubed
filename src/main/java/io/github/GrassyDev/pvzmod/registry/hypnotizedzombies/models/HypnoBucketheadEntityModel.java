package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBucketheadEntity;
import net.fabricmc.example.registry.zombies.zombieentity.BucketheadEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBucketheadEntityModel extends AnimatedGeoModel<HypnoBucketheadEntity> {

    @Override
    public Identifier getModelLocation(HypnoBucketheadEntity object)
    {
        return new Identifier("pvzcubed", "geo/buckethead.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoBucketheadEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/buckethead_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoBucketheadEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}