package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pharaoh;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PharaohVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PharaohEntityModel extends AnimatedGeoModel<PharaohEntity> {

    @Override
    public Identifier getModelResource(PharaohEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(PharaohVariants.SUMMONED) || object.getVariant().equals(PharaohVariants.SUMMONEDHYPNO)){
			identifier = new Identifier ("pvzmod", "geo/pharaoh.geo.json");
		}
		else {
			identifier = new Identifier ("pvzmod", "geo/undyingpharaoh.geo.json");
		}
		return identifier;
    }

    @Override
    public Identifier getTextureResource(PharaohEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_dmg1_geardmg1.png");
		} else if (object.armless && object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_gearless_dmg1.png");
		} else if (object.gearless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PharaohEntity object)
    {
        return new Identifier ("pvzmod", "animations/pharaoh.json");
    }
}
