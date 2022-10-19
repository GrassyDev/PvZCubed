package net.fabricmc.example.registry.zombies.models;

import net.fabricmc.example.registry.zombies.zombieentity.ConeheadEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ConeheadEntityModel extends AnimatedGeoModel<ConeheadEntity> {

    @Override
    public Identifier getModelLocation(ConeheadEntity object)
    {
        return new Identifier("pvzcubed", "geo/conehead.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ConeheadEntity object)
    {
        return new Identifier("pvzcubed", "textures/entity/browncoat/conehead.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ConeheadEntity object)
    {
        return new Identifier ("pvzcubed", "animations/newbrowncoat.json");
    }
}