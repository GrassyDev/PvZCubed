package io.github.GrassyDev.pvzmod.registry.entity.projectileentity;

import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class PvZProjectileEntity extends ThrownItemEntity {

	public PvZProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public PvZProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
		super(entityType, d, e, f, world);
	}

	public PvZProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
		super(entityType, livingEntity, world);
	}

	@Override
	public void tick() {
		this.hitEntities();
		super.tick();
		this.hitEntities.clear();
	}

	protected List<Entity> hitEntities = new ArrayList<>();

	public void hitEntities(){
		List<Entity> hit = this.world.getNonSpectatingEntities(Entity.class, this.getBoundingBox().stretch(0, -0.5, 0));
		for (Entity entity : hit){
			if (entity instanceof GraveEntity graveEntity && graveEntity.decorative){

			}
			else {
				hitEntities.add(entity);
			}
		}
	}
}
