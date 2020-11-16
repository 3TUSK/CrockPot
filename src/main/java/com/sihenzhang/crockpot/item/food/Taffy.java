package com.sihenzhang.crockpot.item.food;

import com.sihenzhang.crockpot.base.CrockPotDamageSource;
import com.sihenzhang.crockpot.item.CrockPotAlwaysEdibleItemFood;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Taffy extends CrockPotAlwaysEdibleItemFood {
    public Taffy() {
        super(5, 0.4F, () -> new EffectInstance(Effects.LUCK, 2 * 60 * 20), FoodUseDuration.FAST);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote) {
            entityLiving.attackEntityFrom(CrockPotDamageSource.TAFFY, 1.0F);
            entityLiving.removePotionEffect(Effects.POISON);
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}