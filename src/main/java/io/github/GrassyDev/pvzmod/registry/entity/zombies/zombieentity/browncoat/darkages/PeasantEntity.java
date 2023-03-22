package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.darkages;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PeasantEntity extends BrowncoatEntity {
	public PeasantEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		if (this.hasStatusEffect(PvZCubed.PVZPOISON)){
			this.removeStatusEffect(PvZCubed.PVZPOISON);
		}
		super.tick();
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)){
			itemStack = ModItems.PEASANTCONEEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)){
			itemStack = ModItems.PEASANTBUCKETEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.PEASANTKNIGHT) || this.getVariant().equals(BrowncoatVariants.PEASANTKNIGHTHYPNO)){
			itemStack = ModItems.PEASANTKNIGHTEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.PEASANTEGG.getDefaultStack();
		}
		return itemStack;
	}
}
