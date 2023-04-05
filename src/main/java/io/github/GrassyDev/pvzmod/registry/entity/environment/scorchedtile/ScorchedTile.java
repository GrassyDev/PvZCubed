package io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.IcebergLettuceSeeds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ScorchedTile extends TileEntity {
	public ScorchedTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 1200){
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/



	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.ICEBERGLETTUCE_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.ICEBERGEXPLOSIONEVENT);
			this.remove(RemovalReason.DISCARDED);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.ICEBERGLETTUCE_SEED_PACKET, IcebergLettuceSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		else {
			return super.interactMob(player, hand);
		}
	}
}
