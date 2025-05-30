package com.yshs.jeiae2compact.client;

import com.yshs.jeiae2compact.util.TagUtil;
import com.yshs.jeiae2compact.util.FavoriteUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(JEIAE2Compact.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // 注册自定义分类
        registration.addRecipeCategories(new TagCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FavoriteCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // 注册标签分类的配方
        registration.addRecipes(TagCategory.TYPE, TagUtil.getAllTaggedItems().stream()
            .collect(Collectors.groupingBy(TagUtil::getTags))
            .entrySet().stream()
            .map(entry -> new TagRecipe(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList()));

        // 注册收藏分类的配方
        registration.addRecipes(FavoriteCategory.TYPE, List.of(new FavoriteRecipe(FavoriteUtil.getFavorites())));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // 注册分类图标
        registration.addRecipeCatalyst(new ItemStack(net.minecraft.world.item.Items.BOOK), TagCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(net.minecraft.world.item.Items.NETHER_STAR), FavoriteCategory.TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // 注册 GUI 处理器
        registration.addGuiContainerHandler(mezz.jei.gui.recipes.RecipesGui.class, new JEIGuiHandler());
    }
} 