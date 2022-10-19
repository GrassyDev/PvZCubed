package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoConeheadEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoConeheadEntityModel extends AnimatedGeoModel<HypnoConeheadEntity> {

    @Override
    public Identifier getModelLocation(HypnoConeheadEntity object)
    {
        return new Identifier("pvzcubed", "geo/conehead.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoConeheadEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/conehead_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoConeheadEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}