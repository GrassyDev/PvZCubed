package io.github.GrassyDev.pvzmod.registry.entity.damage;

import net.minecraft.entity.damage.DamageSource;

public class LightningDamage extends DamageSource {

	public boolean lightningDamage;

	public boolean isLightningDamage() {return this.lightningDamage;}

	public LightningDamage setLightningDamage(){
		this.lightningDamage = true;
		return this;
	}

	public LightningDamage() {
		super("lightningDamage");
	}

	@Override
	public DamageSource setBypassesArmor() {
		return super.setBypassesArmor();
	}
}
