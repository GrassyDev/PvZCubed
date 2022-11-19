package io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DuckyTubeEntityModel extends AnimatedGeoModel<DuckyTubeEntity> {

    @Override
    public Identifier getModelResource(DuckyTubeEntity object)
    {
        return new Identifier("pvzmod", "geo/duckytube.geo.json");
    }

    @Override
    public Identifier getTextureResource(DuckyTubeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/duckytube/duckytube.png");
    }

    @Override
    public Identifier getAnimationResource(DuckyTubeEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
