package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrowncoatEntityModel extends AnimatedGeoModel<BrowncoatEntity> {

    @Override
    public Identifier getModelResource(BrowncoatEntity object)
    {
        return new Identifier("pvzmod", "geo/browncoat.geo.json");
    }

    @Override
    public Identifier getTextureResource(BrowncoatEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
    }

    @Override
    public Identifier getAnimationResource(BrowncoatEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
