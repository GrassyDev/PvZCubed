package io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube;

import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DuckyTubeEntityRenderer extends GeoEntityRenderer<DuckyTubeEntity> {

    public DuckyTubeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DuckyTubeEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
