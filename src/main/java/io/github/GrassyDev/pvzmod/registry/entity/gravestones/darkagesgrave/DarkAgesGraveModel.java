package io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DarkAgesGraveModel extends AnimatedGeoModel<DarkAgesGraveEntity> {

    @Override
    public Identifier getModelResource(DarkAgesGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/darkagesgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(DarkAgesGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(DarkAgesGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
