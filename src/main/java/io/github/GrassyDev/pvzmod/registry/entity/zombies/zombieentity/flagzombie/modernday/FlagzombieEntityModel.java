package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlagzombieEntityModel extends AnimatedGeoModel<FlagzombieEntity> {

    @Override
    public Identifier getModelResource(FlagzombieEntity object)
    {
        return new Identifier("pvzmod", "geo/flagzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(FlagzombieEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(FlagZombieVariants.GAY) || object.getVariant().equals(FlagZombieVariants.GAYHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g_dmg1.png");
			}
		}
		else if (object.getVariant().equals(FlagZombieVariants.TRANS) || object.getVariant().equals(FlagZombieVariants.TRANSHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t_dmg1.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(FlagzombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/flagzombie.json");
    }
}
