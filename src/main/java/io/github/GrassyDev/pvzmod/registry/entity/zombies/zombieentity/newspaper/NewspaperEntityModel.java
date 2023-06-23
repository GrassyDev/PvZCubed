package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.NewspaperVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NewspaperEntityModel extends AnimatedGeoModel<NewspaperEntity> {

    @Override
    public Identifier getModelResource(NewspaperEntity object)
    {
		return NewspaperEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(NewspaperEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(NewspaperVariants.SUNDAYEDITION) || object.getVariant().equals(NewspaperVariants.SUNDAYEDITIONHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/newspaper/sundayedition.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/newspaper/sundayedition_dmg1.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/newspaper/newspaper.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/newspaper/newspaper_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(NewspaperEntity object)
    {
        return new Identifier ("pvzmod", "animations/newspaper.json");
    }
}
