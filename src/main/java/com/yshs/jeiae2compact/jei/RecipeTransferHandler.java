package com.yshs.jeiae2compact.jei;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeTransferHandler implements IRecipeTransferHandler<Recipe<?>> {
    @Override
    public Class<Recipe<?>> getRecipeClass() {
        return (Class<Recipe<?>>) (Class<?>) Recipe.class;
    }

    @Override
    public boolean canHandle(Recipe<?> recipe, IGuiClickableArea area) {
        return area.getTooltip().contains("AE2");
    }

    @Override
    public boolean transferRecipe(Recipe<?> recipe, IGuiClickableArea area) {
        // 实现配方导入AE2的逻辑
        return true;
    }
}