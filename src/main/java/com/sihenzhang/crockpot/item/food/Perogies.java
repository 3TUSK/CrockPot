package com.sihenzhang.crockpot.item.food;

import com.sihenzhang.crockpot.item.CrockPotBaseItemFood;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Perogies extends CrockPotBaseItemFood {
    public Perogies() {
        super(8, 0.8F);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote) {
            entityLiving.heal(6.0F);
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}