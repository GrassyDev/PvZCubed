package io.github.GrassyDev.pvzmod.registry.items;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PeaItem extends Item {
    public PeaItem(Settings settings) {
        super(settings);
    }

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		tooltip.add(Text.translatable("item.pvzmod.creative")
				.formatted(Formatting.UNDERLINE));
	}

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent

        if (!world.isClient) {
            ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, world);
            proj.setPos(user.getX(), user.getY() + 1.5f, user.getZ());
            proj.setOwner(user);
            proj.setProperties(user, user.getPitch(), user.getYaw(), 0, 0.33f, 0);
            world.spawnEntity(proj);
        }

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
            user.getItemCooldownManager().set(this, 30);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

}
