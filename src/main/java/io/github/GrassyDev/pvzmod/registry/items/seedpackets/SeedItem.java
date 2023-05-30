package io.github.GrassyDev.pvzmod.registry.items.seedpackets;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public abstract class SeedItem extends Item {
	public SeedItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		if (entity instanceof PlayerEntity player) {
			if (player.isCreative()){
				nbtCompound.putFloat("Cooldown", 0);
				player.getItemCooldownManager().set(stack.getItem(), 0);
			}
			if (player.getStackInHand(player.getActiveHand()).isItemEqual(stack) && player.getInventory().getEmptySlot() != -1) {
				Vec3d vec3d = new Vec3d(10, 0.0, 0).rotateY(-entity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				HitResult hitResult = world.raycast(new RaycastContext(entity.getPos(), entity.getPos().add(vec3d), RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, entity));
				if (hitResult.getType().equals(HitResult.Type.MISS)) {
					List<Entity> itemEntities = world.getNonSpectatingEntities(Entity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ()).expand(10));
					for (Entity entity1 : itemEntities) {
						if (player.getInventory().getEmptySlot() == -1) {
							break;
						}
						if (entity1 instanceof ItemEntity item && (item.getStack().isItemEqual(ModItems.SUN.getDefaultStack()) ||
								item.getStack().isItemEqual(ModItems.SMALLSUN.getDefaultStack()) ||
								item.getStack().isItemEqual(ModItems.LARGESUN.getDefaultStack()))) {
							player.getInventory().insertStack(item.getStack());
							item.discard();
							player.playSound(PvZSounds.SUNDROPEVENT, 0.5f, 1f);
						}
					}
				}
			}
		}
	}

	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}
}
