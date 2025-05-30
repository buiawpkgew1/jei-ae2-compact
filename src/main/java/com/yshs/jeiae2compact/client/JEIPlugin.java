package com.yshs.jeiae2compact.client;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static IJeiRuntime jeiRuntime;

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.tryParse(JEIAE2Compact.MODID + ":jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        // 暂时不注册任何分类
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // 暂时不注册任何配方
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        jeiRuntime = runtime;
    }

    @Override
    public void onRuntimeUnavailable() {
        jeiRuntime = null;
    }

    public static IJeiRuntime getJeiRuntime() {
        return jeiRuntime;
    }
} 