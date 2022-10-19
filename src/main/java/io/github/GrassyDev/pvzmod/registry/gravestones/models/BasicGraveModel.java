package net.fabricmc.example.registry.gravestones.models;

import net.fabricmc.example.registry.gravestones.gravestoneentity.BasicGraveEntity;
import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BasicGraveModel extends AnimatedGeoModel<BasicGraveEntity> {

    @Override
    public Identifier getModelLocation(BasicGraveEntity object)
    {
        return new Identifier("pvzcubed", "geo/basicgravestone.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BasicGraveEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/gravestone/basicgravestone.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BasicGraveEntity object)
    {
        return new Identifier ("pvzcubed", "animations/gravestone.json");
    }
}