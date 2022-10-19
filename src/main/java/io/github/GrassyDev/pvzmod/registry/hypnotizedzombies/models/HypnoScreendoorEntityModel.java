package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoScreendoorEntity;
import net.fabricmc.example.registry.zombies.zombieentity.ScreendoorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoScreendoorEntityModel extends AnimatedGeoModel<HypnoScreendoorEntity> {

    @Override
    public Identifier getModelLocation(HypnoScreendoorEntity object)
    {
        return new Identifier("pvzcubed", "geo/screendoor.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoScreendoorEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/screendoor_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoScreendoorEntity object)
    {
        return new Identifier ("pvzcubed", "animations/screendoor.json");
    }
}