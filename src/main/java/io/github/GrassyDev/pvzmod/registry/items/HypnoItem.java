package io.github.GrassyDev.pvzmod.registry.items;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class HypnoItem extends Item {
    public HypnoItem(Settings settings) {
        super(settings);
    }

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		entity.damage(PvZCubed.HYPNO_DAMAGE, 0);
		stack.decrement(1);
		return ActionResult.SUCCESS;
	}
}
