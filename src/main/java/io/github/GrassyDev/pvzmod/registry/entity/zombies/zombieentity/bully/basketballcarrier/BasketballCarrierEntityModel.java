package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basketballcarrier;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BasketballCarrierEntityModel extends AnimatedGeoModel<BasketballCarrierEntity> {

    @Override
    public Identifier getModelResource(BasketballCarrierEntity object)
    {
		return BasketballCarrierEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(BasketballCarrierEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/bully/basketballcarrier.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/basketballcarrier_dmg1.png");
		} else if (object.armless && object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/basketballcarrier_dmg1.png");
		} else if (object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/basketballcarrier.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/basketballcarrier.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/basketballcarrier_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BasketballCarrierEntity object)
    {
        return new Identifier ("pvzmod", "animations/bully.json");
    }
}
