package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ImpEntityModel extends AnimatedGeoModel<ImpEntity> {

    @Override
    public Identifier getModelResource(ImpEntity object)
    {
		return ImpEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(ImpEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(ImpVariants.IMPDRAGON) || object.getVariant().equals(ImpVariants.IMPDRAGONHYPNO)) {
			identifier = new Identifier("pvzmod", "textures/entity/imp/impdragon.png");
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/imp/imp.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(ImpEntity object)
    {
        return new Identifier ("pvzmod", "animations/imp.json");
    }
}
