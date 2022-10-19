package net.fabricmc.example.registry.gravestones.models;

import net.fabricmc.example.registry.gravestones.gravestoneentity.BasicGraveEntity;
import net.fabricmc.example.registry.gravestones.gravestoneentity.NightGraveEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NightGraveModel extends AnimatedGeoModel<NightGraveEntity> {

    @Override
    public Identifier getModelLocation(NightGraveEntity object)
    {
        return new Identifier("pvzcubed", "geo/nightgravestone.geo.json");
    }

    @Override
    public Identifier getTextureLocation(NightGraveEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationFileLocation(NightGraveEntity object)
    {
        return new Identifier ("pvzcubed", "animations/gravestone.json");
    }
}