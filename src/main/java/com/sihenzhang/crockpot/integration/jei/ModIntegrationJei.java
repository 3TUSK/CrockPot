package com.sihenzhang.crockpot.integration.jei;

import com.sihenzhang.crockpot.CrockPot;
import com.sihenzhang.crockpot.recipe.CrockPotRecipeTypes;
import com.sihenzhang.crockpot.recipe.FoodValuesDefinition;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class ModIntegrationJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CrockPot.MOD_ID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FoodValuesCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ExplosionCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PiglinBarteringRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        registration.addRecipes(FoodValuesDefinition.getFoodCategoryMatchedItemsList(recipeManager), FoodValuesCategory.UID);
        registration.addRecipes(recipeManager.getAllRecipesFor(CrockPotRecipeTypes.EXPLOSION_CRAFT_RECIPE_TYPE), ExplosionCraftingRecipeCategory.UID);
        registration.addRecipes(recipeManager.getAllRecipesFor(CrockPotRecipeTypes.PIGLIN_BARTERING_RECIPE_TYPE), PiglinBarteringRecipeCategory.UID);
    }
}
