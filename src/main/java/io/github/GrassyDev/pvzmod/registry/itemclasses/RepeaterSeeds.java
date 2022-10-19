package io.github.GrassyDev.pvzmod.registry.itemclasses;

import net.fabricmc.example.registry.PvZEntity;
import net.fabricmc.example.registry.plants.plantentity.RepeaterEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class RepeaterSeeds extends Item {
    public RepeaterSeeds(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        if (direction == Direction.DOWN) {
            return ActionResult.FAIL;
        }
        else if (direction == Direction.SOUTH) {
            return ActionResult.FAIL;
        }
        else if (direction == Direction.EAST) {
            return ActionResult.FAIL;
        }
        else if (direction == Direction.NORTH) {
            return ActionResult.FAIL;
        }
        else if (direction == Direction.WEST) {
            return ActionResult.FAIL;
        }
        else {
            World world = context.getWorld();
            ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
            BlockPos blockPos = itemPlacementContext.getBlockPos();
            ItemStack itemStack = context.getStack();
            Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
            Box box = PvZEntity.REPEATER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
            if (world.isSpaceEmpty((Entity) null, box, (entity) -> {
                return true;
            }) && world.getOtherEntities((Entity) null, box).isEmpty()) {
                if (world instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    RepeaterEntity repeaterEntity = (RepeaterEntity) PvZEntity.REPEATER.create(serverWorld, itemStack.getTag(), (Text) null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
                    if (repeaterEntity == null) {
                        return ActionResult.FAIL;
                    }

                    serverWorld.spawnEntityAndPassengers(repeaterEntity);
                    float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    repeaterEntity.refreshPositionAndAngles(repeaterEntity.getX(), repeaterEntity.getY(), repeaterEntity.getZ(), f, 0.0F);
                    world.spawnEntity(repeaterEntity);
                    world.playSound((PlayerEntity) null, repeaterEntity.getX(), repeaterEntity.getY(), repeaterEntity.getZ(), ExampleMod.PLANTPLANTEDEVENT, SoundCategory.BLOCKS, 0.75F, 0.8F);
                }

                PlayerEntity user = context.getPlayer();
                if (!user.abilities.creativeMode) {
                    itemStack.decrement(1);
                    user.getItemCooldownManager().set(this, 150);
                }
                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.FAIL;
            }
        }
    }
}