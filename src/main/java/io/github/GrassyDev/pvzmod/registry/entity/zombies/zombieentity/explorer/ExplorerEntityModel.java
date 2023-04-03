package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.explorer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ExplorerEntityModel extends AnimatedGeoModel<ExplorerEntity> {

    @Override
    public Identifier getModelResource(ExplorerEntity object)
    {
        return new Identifier("pvzmod", "geo/explorer.geo.json");
    }

    @Override
    public Identifier getTextureResource(ExplorerEntity object)
    {
		Identifier identifier;
		if (object.getHypno()) {
			if (object.getFireStage()) {
				identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer_hypnotized.png");
				if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer_dmg1_hypnotized.png");
				}
			}
			else {
				identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer_hypnotized_extinguished.png");
				if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer_dmg1_hypnotized_extinguished.png");
				}
			}
		}
		else {
			if (object.getFireStage()) {
				identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer.png");
				if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer_dmg1.png");
				}
			}
			else {
				identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer_extinguished.png");
				if (object.armless) {
					identifier = new Identifier("pvzmod", "textures/entity/explorer/explorer_dmg1_extinguished.png");
				}
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(ExplorerEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
