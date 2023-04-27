package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SuperFanImpEntityModel extends AnimatedGeoModel<SuperFanImpEntity> {

    @Override
    public Identifier getModelResource(SuperFanImpEntity object)
    {
        return new Identifier("pvzmod", "geo/superfanimp.geo.json");
    }

    @Override
    public Identifier getTextureResource(SuperFanImpEntity object) {
		Identifier identifier;
		if (object.getVariant().equals(ImpVariants.NEWYEAR) || object.getVariant().equals(ImpVariants.NEWYEARHYPNO)) {
			identifier = new Identifier("pvzmod", "textures/entity/imp/newyearimp.png");
		}
		else{
			identifier = new Identifier("pvzmod", "textures/entity/imp/superfanimp.png");
		}
		return identifier;
	}

    @Override
    public Identifier getAnimationResource(SuperFanImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
