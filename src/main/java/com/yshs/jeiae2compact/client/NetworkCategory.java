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
import net.minecraft.world.item.ItemStack;
import java.util.List;

public class NetworkCategory implements IRecipeCategory<ItemStack> {
    public static final RecipeType<ItemStack> TYPE = RecipeType.create(JEIAE2Compact.MODID, "network", ItemStack.class);
    private final IDrawable background;
    private final IDrawable icon;

    public NetworkCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(160, 100);
        this.icon = guiHelper.createDrawable(
            ResourceLocation.tryParse(JEIAE2Compact.MODID + ":textures/gui/network_icon.png"),
            0, 0, 16, 16);
    }

    @Override
    public RecipeType<ItemStack> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category." + JEIAE2Compact.MODID + ".network");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ItemStack recipe, IFocusGroup focuses) {
        builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 20, 20)
            .addItemStack(recipe);

        if (recipe.getCount() > 1) {
            builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 20, 40)
                .addItemStack(new ItemStack(recipe.getItem(), recipe.getCount()));
        }
    }
} 