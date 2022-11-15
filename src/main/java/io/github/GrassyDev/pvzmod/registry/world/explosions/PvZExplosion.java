package io.github.GrassyDev.pvzmod.registry.world.explosions;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.SummonerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.*;

public class PvZExplosion extends Explosion {
    private final Explosion.DestructionType blockDestructionType;
    private final Random random;
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    private final Entity entity;
	private final float damage;
    private final float rangedoubled;
	private boolean fire;
	private boolean hypnosis;
    private final DamageSource damageSource;
    private final List<BlockPos> affectedBlocks;
    private final Map<PlayerEntity, Vec3d> affectedPlayers;


    public PvZExplosion(World world, Entity entity, double x, double y, double z, float damage, float rangedoubled, DestructionType destructionType, DestructionType none, boolean fire) {
        super(world, entity, x, y, z, rangedoubled, false, destructionType);
		this.random = new Random();
        this.affectedBlocks = Lists.newArrayList();
        this.affectedPlayers = Maps.newHashMap();
        this.world = world;
        this.entity = entity;
        this.damage = damage;
		this.rangedoubled = rangedoubled;
        this.x = x;
        this.y = y;
        this.z = z;;
        this.blockDestructionType = destructionType;
        this.damageSource = DamageSource.explosion(this);
		this.fire = fire;
    }



	public void affectWorld(boolean particles) {
		boolean bl = this.blockDestructionType == Explosion.DestructionType.NONE;
		if (particles) {
			if (!(this.rangedoubled < 2.0F) && bl) {
				this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
			} else {
				this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
			}
		}
	}

	public boolean setHypnosis(boolean hypnosis) {
		return this.hypnosis = hypnosis;
	}

    public void collectBlocksAndDamageEntities() {
        Set<BlockPos> set = Sets.newHashSet();

        int k;
        int l;
        for(int j = 0; j < 16; ++j) {
            for(k = 0; k < 16; ++k) {
                for(l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d = (float)j / 15.0F * 2.0F - 1.0F;
                        double e = (float)k / 15.0F * 2.0F - 1.0F;
                        double f = (float)l / 15.0F * 2.0F - 1.0F;
                        double g = Math.sqrt(d * d + e * e + f * f);
                        d /= g;
                        e /= g;
                        f /= g;
                        float h = this.rangedoubled * (0.7F + this.world.random.nextFloat() * 0.6F);
                        double m = this.x;
                        double n = this.y;
                        double o = this.z;

                    }
                }
            }
        }

        this.affectedBlocks.addAll(set);
        float q = this.rangedoubled * 2.0F;
        k = MathHelper.floor(this.x - (double)q - 1.0D);
        l = MathHelper.floor(this.x + (double)q + 1.0D);
        int t = MathHelper.floor(this.y - (double)q - 1.0D);
        int u = MathHelper.floor(this.y + (double)q + 1.0D);
        int v = MathHelper.floor(this.z - (double)q - 1.0D);
        int w = MathHelper.floor(this.z + (double)q + 1.0D);
        List<Entity> list = this.world.getOtherEntities(this.entity, new Box(k, t, v, l, u, w));
        Vec3d vec3d = new Vec3d(this.x, this.y, this.z);

        for(int x = 0; x < list.size(); ++x) {
            Entity entity = list.get(x);
			if (!entity.isImmuneToExplosion() && entity instanceof Monster && !(entity instanceof HypnoSummonerEntity)) {
                double y = Math.sqrt(entity.squaredDistanceTo(vec3d)) / q;
                if (y <= 1.0D) {
                    double z = entity.getX() - this.x;
                    double aa = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.y;
                    double ab = entity.getZ() - this.z;
                    double ac = Math.sqrt(z * z + aa * aa + ab * ab);
                    if (ac != 0.0D) {
						z /= ac;
						aa /= ac;
						ab /= ac;
						double ad = getExposure(vec3d, entity);
						double ae = (1.0D - y) * ad;
						float kk = this.damage;
						entity.damage(this.getDamageSource(), kk);
						assert entity instanceof LivingEntity;
						if (this.fire) {
							((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
							((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
							((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 40, 5)));
							entity.setOnFireFor(4);
						}
						if (this.hypnosis && (entity instanceof PvZombieEntity || entity instanceof SummonerEntity)) {
							entity.damage(PvZCubed.HYPNO_DAMAGE, 0);
						}
					}
                }
            }
        }
    }
}
