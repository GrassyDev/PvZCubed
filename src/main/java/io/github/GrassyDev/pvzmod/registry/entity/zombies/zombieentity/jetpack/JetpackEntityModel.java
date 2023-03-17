package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.jetpack;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.JetpackVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JetpackEntityModel extends AnimatedGeoModel<JetpackEntity> {

    @Override
    public Identifier getModelResource(JetpackEntity object)
    {
		return JetpackEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(JetpackEntity object)
	{
		Identifier identifier;
		if (object.getVariant().equals(JetpackVariants.BLASTRONAUT) || object.getVariant().equals(JetpackVariants.BLASTRONAUTHYPNO)){
			if (object.getHypno()){
				identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_hypnotized.png");
				if (object.armless && object.geardmg){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_dmg1_geardmg1_hypnotized.png");
				}
				else if (object.armless && object.gearless){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_gearless_dmg1_hypnotized.png");
				}
				else if (object.gearless){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_gearless_hypnotized.png");
				}
				else if (object.geardmg){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_geardmg1_hypnotized.png");
				}
				else if (object.armless){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_dmg1_hypnotized.png");
				}
			}
			else {
				identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut.png");
				if (object.armless && object.geardmg){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_dmg1_geardmg1.png");
				}
				else if (object.armless && object.gearless){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_gearless_dmg1.png");
				}
				else if (object.gearless){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_gearless.png");
				}
				else if (object.geardmg){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_geardmg1.png");
				}
				else if (object.armless){
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/blastronaut_dmg1.png");
				}
			}
		}
		else {
			if (object.getHypno()) {
				identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_hypnotized.png");
				if (object.armless && object.geardmg) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_dmg1_hypnotized.png");
				} else if (object.armless && object.gearless) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_dmg1_hypnotized.png");
				} else if (object.gearless) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_hypnotized.png");
				} else if (object.geardmg) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_hypnotized.png");
				} else if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_dmg1_hypnotized.png");
				}
			} else {
				identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack.png");
				if (object.armless && object.geardmg) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_dmg1.png");
				} else if (object.armless && object.gearless) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_dmg1.png");
				} else if (object.gearless) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack.png");
				} else if (object.geardmg) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack.png");
				} else if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/jetpack/jetpack_dmg1.png");
				}
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(JetpackEntity object)
    {
        return new Identifier ("pvzmod", "animations/jetpack.json");
    }
}
