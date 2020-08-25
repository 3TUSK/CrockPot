package com.sihenzhang.crockpot.recipe.requirements;

import com.sihenzhang.crockpot.base.CrockPotIngredientType;
import com.sihenzhang.crockpot.recipe.RecipeInput;
import net.minecraft.nbt.CompoundNBT;

public class RequirementIngredientMin extends Requirement {
    CrockPotIngredientType type;
    float min;

    public RequirementIngredientMin(CrockPotIngredientType type, float min) {
        this.type = type;
        this.min = min;
    }

    public RequirementIngredientMin(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public CrockPotIngredientType getType() {
        return type;
    }

    public float getMin() {
        return min;
    }

    @Override
    public boolean test(RecipeInput recipeInput) {
        return recipeInput.ingredients.getIngredient(type) >= min;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("type", "ingredient_min");
        nbt.putString("ingredient", type.name());
        nbt.putFloat("min", min);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (!"ingredient_min".equals(nbt.getString("type"))) {
            throw new IllegalArgumentException("requirement type doesn't match");
        }
        this.min = nbt.getFloat("min");
        this.type = CrockPotIngredientType.valueOf(nbt.getString("ingredient"));
    }
}
