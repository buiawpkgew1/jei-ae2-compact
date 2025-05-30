package com.yshs.jeiae2compact.client;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeLayoutBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;

public class TagCategory implements IRecipeCategory<TagRecipe> {
    public static final RecipeType<TagRecipe> TYPE = RecipeType.create(JEIAE2Compact.MODID, "tags", TagRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public TagCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(160, 100);
        this.icon = guiHelper.createDrawable(
            ResourceLocation.tryParse(JEIAE2Compact.MODID + ":textures/gui/tag_icon.png"),
            0, 0, 16, 16);
    }

    @Override
    public RecipeType<TagRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category." + JEIAE2Compact.MODID + ".tags");
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
    public void setRecipe(IRecipeLayoutBuilder builder, TagRecipe recipe, IFocusGroup focuses) {
        // 设置物品槽位
        builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 20, 20)
            .addItemStacks(recipe.getItems());
    }
} 