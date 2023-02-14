package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.bubbles;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BubbleEntityModel extends AnimatedGeoModel<BubbleEntity> {

    @Override
    public Identifier getModelResource(BubbleEntity object)
    {
        return new Identifier("pvzmod", "geo/blank.geo.json");
    }

    @Override
    public Identifier getTextureResource(BubbleEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/projectiles/fume.png");
    }

    @Override
    public Identifier getAnimationResource(BubbleEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
