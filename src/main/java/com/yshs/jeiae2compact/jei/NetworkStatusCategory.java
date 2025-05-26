package com.yshs.jeiae2compact.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class NetworkStatusCategory implements IRecipeCategory<NetworkStatusRecipe> {
    
    private final IDrawable background;
    private final IDrawable icon;
    
    public NetworkStatusCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(150, 100);
        this.icon = guiHelper.createDrawable(new ResourceLocation(JEIAE2Compact.MODID, "textures/gui/network_icon.png"), 0, 0, 16, 16);
    }
    
    @Override
    public RecipeType<NetworkStatusRecipe> getRecipeType() {
        return new RecipeType<>(new ResourceLocation(JEIAE2Compact.MODID, "network_status"), NetworkStatusRecipe.class);
    }
    
    @Override
    public Component getTitle() {
        return Component.translatable("jei.category." + JEIAE2Compact.MODID + ".network_status");
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
    public void setRecipe(IRecipeLayoutBuilder builder, NetworkStatusRecipe recipe, IFocusGroup focuses) {
        // 在这里设置配方布局
    }
}