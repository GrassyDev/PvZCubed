package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BrowncoatEntityModel extends AnimatedGeoModel<BrowncoatEntity> {

    @Override
    public Identifier getModelResource(BrowncoatEntity object)
    {
		return BrowncoatEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(BrowncoatEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1_geardmg1.png");
		} else if (object.armless && object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_gearless_dmg1.png");
		} else if (object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BrowncoatEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
