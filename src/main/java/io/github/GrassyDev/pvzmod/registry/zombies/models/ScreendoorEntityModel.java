package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.fabricmc.example.registry.zombies.zombieentity.ScreendoorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScreendoorEntityModel extends AnimatedGeoModel<ScreendoorEntity> {

    @Override
    public Identifier getModelLocation(ScreendoorEntity object)
    {
        return new Identifier("pvzcubed", "geo/screendoor.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ScreendoorEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/screendoor.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ScreendoorEntity object)
    {
        return new Identifier ("pvzcubed", "animations/screendoor.json");
    }
}