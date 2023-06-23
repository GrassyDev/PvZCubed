package io.github.GrassyDev.pvzmod.registry.items.toolclasses;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PlantKillingMaterial implements ToolMaterial {

    public static final PlantKillingMaterial INSTANCE = new PlantKillingMaterial();

    @Override
    public float getAttackDamage() {
        return 999.0f;
    }

    @Override
    public int getDurability() {
        return 30;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1.0F;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }
}
