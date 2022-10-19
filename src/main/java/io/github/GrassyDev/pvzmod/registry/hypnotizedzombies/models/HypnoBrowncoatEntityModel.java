package net.fabricmc.example.registry.hypnotizedzombies.models;

import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoBrowncoatEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HypnoBrowncoatEntityModel extends AnimatedGeoModel<HypnoBrowncoatEntity> {

    @Override
    public Identifier getModelLocation(HypnoBrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "geo/browncoat.geo.json");
    }

    @Override
    public Identifier getTextureLocation(HypnoBrowncoatEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/browncoat_hypnotized.png");
    }

    @Override
    public Identifier getAnimationFileLocation(HypnoBrowncoatEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}