package io.github.GrassyDev.pvzmod.registry.itemclasses;

import net.fabricmc.example.registry.PvZEntity;
import net.fabricmc.example.registry.zombies.zombieentity.DancingZombieEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DancingZombieEgg extends Item {
    public DancingZombieEgg(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        BlockPos blockPos = itemPlacementContext.getBlockPos();
        ItemStack itemStack = context.getStack();
        Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
        Box box = PvZEntity.DANCINGZOMBIE.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
            if (world.isSpaceEmpty((Entity) null, box, (entity) -> {
                return true;
            }) && world.getOtherEntities((Entity) null, box).isEmpty()) {
                if (world instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    DancingZombieEntity dancingZombieEntity = (DancingZombieEntity) PvZEntity.DANCINGZOMBIE.create(serverWorld, itemStack.getTag(), (Text) null, context.getPlayer(), blockPos, SpawnReason.SPAWN_EGG, true, true);
                    if (dancingZombieEntity == null) {
                        return ActionResult.FAIL;
                    }

                    serverWorld.spawnEntityAndPassengers(dancingZombieEntity);
                    float f = (float) MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    dancingZombieEntity.refreshPositionAndAngles(dancingZombieEntity.getX(), dancingZombieEntity.getY(), dancingZombieEntity.getZ(), f, 0.0F);
                    world.spawnEntity(dancingZombieEntity);
                    world.playSound((PlayerEntity) null, dancingZombieEntity.getX(), dancingZombieEntity.getY(), dancingZombieEntity.getZ(), ExampleMod.ENTITYRISINGEVENT, SoundCategory.BLOCKS, 0.75F, 0.8F);
                }

                itemStack.decrement(1);
                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.FAIL;
            }
    }
}
