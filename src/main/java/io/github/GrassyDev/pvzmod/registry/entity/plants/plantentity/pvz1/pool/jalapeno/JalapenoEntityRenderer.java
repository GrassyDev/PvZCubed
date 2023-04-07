package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class JalapenoEntityRenderer extends GeoEntityRenderer<JalapenoEntity> {

    public JalapenoEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new JalapenoEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(CherrybombEntity plantEntity, BlockPos blockPos) {
		return plantEntity.getFuseSpeed() > 0 ? 10 : 0;
	}

}
