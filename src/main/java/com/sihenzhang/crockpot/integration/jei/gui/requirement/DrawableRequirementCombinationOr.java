package com.sihenzhang.crockpot.integration.jei.gui.requirement;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sihenzhang.crockpot.recipe.cooking.requirement.IRequirement;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCombinationOr;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class DrawableRequirementCombinationOr extends AbstractDrawableRequirement<RequirementCombinationOr> {
    private final AbstractDrawableRequirement<? extends IRequirement> first, second;

    public DrawableRequirementCombinationOr(RequirementCombinationOr requirement) {
        super(requirement, new TranslationTextComponent("integration.crockpot.jei.crock_pot_cooking.requirement.or"));
        this.first = AbstractDrawableRequirement.createDrawable(requirement.getFirst());
        this.second = AbstractDrawableRequirement.createDrawable(requirement.getSecond());
    }

    @Override
    public int getWidth() {
        return 8 + first.getWidth() + second.getWidth() + Minecraft.getInstance().font.width(description);
    }

    @Override
    public int getHeight() {
        return 6 + Math.max(first.getHeight(), second.getHeight());
    }

    @Override
    public void draw(MatrixStack matrixStack, int xOffset, int yOffset) {
        super.draw(matrixStack, xOffset, yOffset);
        first.draw(matrixStack, xOffset + 3, yOffset + (this.getHeight() / 2) - (first.getHeight() / 2));
        Minecraft.getInstance().font.draw(matrixStack, description, xOffset + first.getWidth() + 4, yOffset + (this.getHeight() / 2.0F) - 4, 0);
        second.draw(matrixStack, xOffset + this.getWidth() - second.getWidth() - 3, yOffset + (this.getHeight() / 2) - (second.getHeight() / 2));
    }

    @Override
    public List<List<ItemStack>> getInputLists() {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        builder.addAll(first.getInputLists());
        builder.addAll(second.getInputLists());
        return builder.build();
    }

    @Override
    public List<GuiItemStacksInfo> getGuiItemStacksInfos(int xOffset, int yOffset) {
        ImmutableList.Builder<GuiItemStacksInfo> builder = ImmutableList.builder();
        builder.addAll(first.getGuiItemStacksInfos(xOffset + 3, yOffset + (this.getHeight() / 2) - (first.getHeight() / 2)));
        builder.addAll(second.getGuiItemStacksInfos(xOffset + this.getWidth() - second.getWidth() - 3, yOffset + (this.getHeight() / 2) - (second.getHeight() / 2)));
        return builder.build();
    }
}
