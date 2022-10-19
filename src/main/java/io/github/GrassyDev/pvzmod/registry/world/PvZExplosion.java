package net.fabricmc.example.registry.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import net.fabricmc.example.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import net.fabricmc.example.registry.plants.plantentity.CherrybombEntity;
import net.fabricmc.example.registry.plants.plantentity.PeashooterEntity;
import net.fabricmc.example.registry.plants.plantentity.SunflowerEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PvZExplosion extends Explosion {
    private final Explosion.DestructionType blockDestructionType;
    private final Random random;
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    private final Entity entity;
    private final float power;
    private final DamageSource damageSource;
    private final List<BlockPos> affectedBlocks;
    private final Map<PlayerEntity, Vec3d> affectedPlayers;


    public PvZExplosion(World world, Entity entity, double x, double y, double z, float power, DestructionType destructionType, DestructionType none) {
        super(world, entity, null, null, x, y, z, power, false, destructionType);
        this.random = new Random();
        this.affectedBlocks = Lists.newArrayList();
        this.affectedPlayers = Maps.newHashMap();
        this.world = world;
        this.entity = entity;
        this.power = power;
        this.x = x;
        this.y = y;
        this.z = z;;
        this.blockDestructionType = destructionType;
        this.damageSource = DamageSource.explosion(this);
    }

    public float getPower() {
        return power;
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
                        float h = this.power * (0.7F + this.world.random.nextFloat() * 0.6F);
                        double m = this.x;
                        double n = this.y;
                        double o = this.z;

                    }
                }
            }
        }

        this.affectedBlocks.addAll(set);
        float q = this.power * 2.0F;
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
            if (!entity.isImmuneToExplosion()) {
                double y = MathHelper.sqrt(entity.squaredDistanceTo(vec3d)) / q;
                if (y <= 1.0D) {
                    double z = entity.getX() - this.x;
                    double aa = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.y;
                    double ab = entity.getZ() - this.z;
                    double ac = MathHelper.sqrt(z * z + aa * aa + ab * ab);
                    if (ac != 0.0D) {
                        z /= ac;
                        aa /= ac;
                        ab /= ac;
                        double ad = getExposure(vec3d, entity);
                        double ae = (1.0D - y) * ad;
                        if (entity instanceof Monster && !(entity instanceof HypnoDancingZombieEntity) &&
                                !(entity instanceof HypnoFlagzombieEntity)) {
                            entity.damage(this.getDamageSource(), 180f);
                        }
                    }
                }
            }
        }

    }

}