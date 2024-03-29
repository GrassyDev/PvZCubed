package io.github.GrassyDev.pvzmod.mixin;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.SeedItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ShovelItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.GrassyDev.pvzmod.PvZCubed.MAX_REACH_UUID;
import static io.github.GrassyDev.pvzmod.PvZCubed.createReachModifier;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract PlayerInventory getInventory();

	@Shadow
	public abstract boolean isCreative();

	@Inject(method = "tick", at = @At("HEAD"))
    public void pvzmod$tick(CallbackInfo ci) {
		if ((this.getInventory().getMainHandStack().getItem() instanceof SeedItem
				|| this.getInventory().getMainHandStack().getItem() instanceof ShovelItem) && (this.getOffHandStack().isEmpty() || this.getOffHandStack().isItemEqual(ModItems.SUN.getDefaultStack())) &&
				!this.getAttributes().hasModifierForAttribute(ReachEntityAttributes.REACH, MAX_REACH_UUID)){
			EntityAttributeInstance maxReachAttribute = this.getAttributeInstance(ReachEntityAttributes.REACH);
			assert maxReachAttribute != null;
			maxReachAttribute.removeModifier(MAX_REACH_UUID);
			maxReachAttribute.addPersistentModifier(createReachModifier(15D));
		}
		if ((!(this.getInventory().getMainHandStack().getItem() instanceof SeedItem) && !(this.getInventory().getMainHandStack().getItem() instanceof ShovelItem))  ||
				(!this.getOffHandStack().isEmpty() && !this.getOffHandStack().isItemEqual(ModItems.SUN.getDefaultStack())) && this.getAttributes().hasModifierForAttribute(ReachEntityAttributes.REACH, MAX_REACH_UUID)) {
			EntityAttributeInstance maxReachAttribute = this.getAttributeInstance(ReachEntityAttributes.REACH);
			assert maxReachAttribute != null;
			maxReachAttribute.removeModifier(MAX_REACH_UUID);
		}
    }
}
