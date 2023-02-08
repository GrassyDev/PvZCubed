package io.github.GrassyDev.pvzmod.registry.items;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle.JingleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class JingleItem extends Item {
    public JingleItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.NEUTRAL, 10F, 1F); // plays a globalSoundEvent

        if (!world.isClient) {
            JingleEntity proj = new JingleEntity(PvZEntity.JINGLE, world);
            proj.setPos(user.getX(), user.getY() + 1.5f, user.getZ());
            proj.setOwner(user);
            proj.setProperties(user, user.getPitch(), user.getYaw(), 0, 0.85F, 0);
            world.spawnEntity(proj);
        }

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
            user.getItemCooldownManager().set(this, 20);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

}
