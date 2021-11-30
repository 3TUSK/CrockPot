package com.sihenzhang.crockpot.integration.jei.gui.requirement;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sihenzhang.crockpot.base.FoodCategory;
import com.sihenzhang.crockpot.recipe.FoodValuesDefinition;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCategoryMinExclusive;
import com.sihenzhang.crockpot.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class DrawableRequirementCategoryMinExclusive extends AbstractDrawableRequirement<RequirementCategoryMinExclusive> {
    public DrawableRequirementCategoryMinExclusive(RequirementCategoryMinExclusive requirement) {
        super(requirement, MathUtils.fuzzyEquals(requirement.getMin(), 0.0F) ? new TranslationTextComponent("integration.crockpot.jei.crock_pot_cooking.requirement.any") : new TranslationTextComponent("integration.crockpot.jei.crock_pot_cooking.requirement.gt", requirement.getMin()));
    }

    @Override
    public int getWidth() {
        return 23 + Minecraft.getInstance().font.width(description);
    }

    @Override
    public int getHeight() {
        return 22;
    }

    @Override
    public void draw(MatrixStack matrixStack, int xOffset, int yOffset) {
        super.draw(matrixStack, xOffset, yOffset);
        Minecraft.getInstance().font.draw(matrixStack, description, MathUtils.fuzzyEquals(requirement.getMin(), 0.0F) ? xOffset + 3 : xOffset + 20, yOffset + 7, 0);
    }

    @Override
    public List<List<ItemStack>> getInputLists() {
        return ImmutableList.of(ImmutableList.of(FoodCategory.getItemStack(requirement.getCategory())), ImmutableList.copyOf(FoodValuesDefinition.getMatchedItems(requirement.getCategory(), Minecraft.getInstance().level.getRecipeManager()).stream().map(Item::getDefaultInstance).iterator()));
    }

    @Override
    public List<GuiItemStacksInfo> getGuiItemStacksInfos(int xOffset, int yOffset) {
        return ImmutableList.of(new GuiItemStacksInfo(ImmutableList.of(FoodCategory.getItemStack(requirement.getCategory())), MathUtils.fuzzyEquals(requirement.getMin(), 0.0F) ? xOffset + this.getWidth() - 19 : xOffset + 3, yOffset + 3));
    }
}