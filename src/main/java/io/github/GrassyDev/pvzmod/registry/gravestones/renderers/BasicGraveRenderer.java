package io.github.GrassyDev.pvzmod.registry.gravestones.renderers;

import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.gravestones.models.BasicGraveModel;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BasicGraveRenderer extends GeoEntityRenderer<BasicGraveEntity> {

    public BasicGraveRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BasicGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
