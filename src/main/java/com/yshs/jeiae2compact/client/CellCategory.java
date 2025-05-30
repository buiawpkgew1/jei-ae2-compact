package com.yshs.jeiae2compact.client;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;
import com.yshs.jeiae2compact.util.AE2ItemUtil;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public class CellCategory implements IRecipeCategory<CellRecipe> {
    public static final RecipeType<CellRecipe> TYPE = RecipeType.create(JEIAE2Compact.MODID, "cells", CellRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public CellCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(160, 100);
        this.icon = guiHelper.createDrawable(
            ResourceLocation.tryParse(JEIAE2Compact.MODID + ":textures/gui/cell_icon.png"),
            0, 0, 16, 16);
    }

    @Override
    public RecipeType<CellRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category." + JEIAE2Compact.MODID + ".cells");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CellRecipe recipe, IFocusGroup focuses) {
        // 显示存储单元中的物品
        List<ItemStack> items = recipe.getItems();
        int x = 20;
        int y = 20;
        int itemsPerRow = 8;
        
        for (int i = 0; i < items.size(); i++) {
            int row = i / itemsPerRow;
            int col = i % itemsPerRow;
            builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 
                x + col * 18, y + row * 18)
                .addItemStack(items.get(i));
        }
    }
} 