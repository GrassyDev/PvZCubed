package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoNewspaperEntity;
import net.fabricmc.example.registry.zombies.zombieentity.NewspaperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoNewspaperEntityModel extends AnimatedGeoModel<HypnoNewspaperEntity> {

    @Override
    public Identifier getModelLocation(HypnoNewspaperEntity object)
    {
        return new Identifier("pvzcubed", "geo/newspaper.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoNewspaperEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/newspaper/newspaper_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoNewspaperEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newspaper.json");
    }
}