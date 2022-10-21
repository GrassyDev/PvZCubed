package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SporeEntityRenderer extends GeoProjectilesRenderer {

	public SporeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SporeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
		this.widthScale = 0.25F;
		this.heightScale = 0.25F;
	}

}
