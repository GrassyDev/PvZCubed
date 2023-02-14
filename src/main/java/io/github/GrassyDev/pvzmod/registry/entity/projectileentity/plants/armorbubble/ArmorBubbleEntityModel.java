package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.armorbubble;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArmorBubbleEntityModel extends AnimatedGeoModel<ArmorBubbleEntity> {

    @Override
    public Identifier getModelResource(ArmorBubbleEntity object)
    {
        return new Identifier("pvzmod", "geo/bigpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArmorBubbleEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/armorbubble.png");
    }

    @Override
    public Identifier getAnimationResource(ArmorBubbleEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
