package io.github.GrassyDev.pvzmod.registry.damage;

import net.minecraft.entity.damage.DamageSource;

public class HypnoDamage extends DamageSource {

	public boolean hypnoDamage;

	public boolean isHypnoDamage() {return this.hypnoDamage;}

	public HypnoDamage setHypnoDamage(){
		this.hypnoDamage = true;
		return this;
	}

	public HypnoDamage() {
		super("hypnoDamage");
	}

	@Override
	public DamageSource setBypassesArmor() {
		return super.setBypassesArmor();
	}
}
