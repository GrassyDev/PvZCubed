package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dolphinrider;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DolphinRiderEntityModel extends AnimatedGeoModel<DolphinRiderEntity> {

    @Override
    public Identifier getModelResource(DolphinRiderEntity object)
    {
        return new Identifier("pvzmod", "geo/dolphinrider.geo.json");
    }

    @Override
    public Identifier getTextureResource(DolphinRiderEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/dolphinrider/dolphinrider.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/dolphinrider/dolphinrider_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(DolphinRiderEntity object)
    {
        return new Identifier ("pvzmod", "animations/dolphinrider.json");
    }
}
