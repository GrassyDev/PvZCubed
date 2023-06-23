package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GardeningGloves extends Item {
    public GardeningGloves(Settings settings) {
        super(settings);
    }

	//Credits to Patchouli for the tooltip code!
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.gardeninggloves")
				.formatted(Formatting.LIGHT_PURPLE));
		tooltip.add(Text.translatable("item.pvzmod.gardeninggloves2")
				.formatted(Formatting.LIGHT_PURPLE));
	}
}
