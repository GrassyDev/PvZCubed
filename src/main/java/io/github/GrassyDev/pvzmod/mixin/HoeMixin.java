package io.github.GrassyDev.pvzmod.mixin;

import com.mojang.datafixers.util.Pair;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

@Mixin(HoeItem.class)
public abstract class HoeMixin {

	@Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void pvzmod$useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, BlockPos blockPos, Pair pair, Predicate predicate, Consumer consumer, PlayerEntity playerEntity) {
		double random2 = Math.random();
		if (random2 <= 0.2 && PVZCONFIG.nestedSpawns.hoeAlternative()) {
			Item item2 = ModItems.PLANTFOOD_LIST.get(world.getRandom().nextInt(ModItems.PLANTFOOD_LIST.size()));
			ItemEntity itemEntity = new ItemEntity(world, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), item2.getDefaultStack());
			itemEntity.setToDefaultPickupDelay();
			world.spawnEntity(itemEntity);
			context.getStack().damage(PVZCONFIG.nestedSpawns.hoeBreak(), playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
		}
    }
}
