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
    // 实现完整的配方布局
    public void setRecipe(IRecipeLayoutBuilder builder, NetworkStatusRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 10)
               .addTooltipCallback((recipeSlotView, tooltip) -> {
                   tooltip.add(Component.translatable("jeiae2compact.tooltip.network_status"));
                   tooltip.addAll(recipe.getTooltips());
               });
        
        // 添加网络状态显示区域
        builder.addSlot(RecipeIngredientRole.OUTPUT, 50, 10)
               .setCustomRenderer(NetworkStatusRenderer.class)
               .addIngredient(NetworkStatusIngredient.TYPE, recipe.getStatus());
    }
}