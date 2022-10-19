package net.fabricmc.example.registry.toolclasses;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class PlantKillingShovel extends ShovelItem {
    public PlantKillingShovel(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}