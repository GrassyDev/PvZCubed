package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.NewspaperEntity;
import net.fabricmc.example.registry.zombies.zombieentity.ScreendoorEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NewspaperEntityModel extends AnimatedGeoModel<NewspaperEntity> {

    @Override
    public Identifier getModelLocation(NewspaperEntity object)
    {
        return new Identifier("pvzcubed", "geo/newspaper.geo.json");
    }

    @Override
    public Identifier getTextureLocation(NewspaperEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/newspaper/newspaper.png");
    }

    @Override
    public Identifier getAnimationFileLocation(NewspaperEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newspaper.json");
    }
}