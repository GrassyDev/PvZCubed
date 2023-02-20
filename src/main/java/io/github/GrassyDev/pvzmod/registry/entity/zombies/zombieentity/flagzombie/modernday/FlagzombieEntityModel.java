package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
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
		if (object.getType().equals(PvZEntity.FLAGZOMBIE_G)){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g_dmg1.png");
			}
		}
		else if (object.getType().equals(PvZEntity.FLAGZOMBIE_T)){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t_dmg1.png");
			}
		}
		else if (object.getType().equals(PvZEntity.FLAGZOMBIEHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_hypnotized.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1_hypnotized.png");
			}
		}
		else if (object.getType().equals(PvZEntity.FLAGZOMBIE_GHYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g_hypnotized.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_g_dmg1_hypnotized.png");
			}
		}
		else if (object.getType().equals(PvZEntity.FLAGZOMBIE_THYPNO)){
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t_hypnotized.png");
			if (object.armless){
				identifier = new Identifier("pvzmod", "textures/entity/browncoat/flagzombie/browncoat_t_dmg1_hypnotized.png");
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
