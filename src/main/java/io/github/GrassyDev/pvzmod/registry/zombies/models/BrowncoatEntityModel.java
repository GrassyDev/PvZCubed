package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.BrowncoatEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrowncoatEntityModel extends AnimatedGeoModel<BrowncoatEntity> {

    @Override
    public Identifier getModelLocation(BrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "geo/browncoat.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/browncoat.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BrowncoatEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}