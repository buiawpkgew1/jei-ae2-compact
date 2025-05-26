package com.yshs.jeiae2compact.jei;

import com.yshs.jeiae2compact.JEIAE2Compact;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 插件注册
 */
@JeiPlugin
public class JEIAE2CompactPlugin implements IModPlugin {

    private static IJeiRuntime jeiRuntime;

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(JEIAE2Compact.MODID, "jei");
    }

    @Override
public void onRuntimeAvailable(@NotNull IJeiRuntime runtime) {
    jeiRuntime = runtime;
    
    // 注册网络状态分类
    runtime.getRecipeManager().addRecipes(
        RecipeTypes.create(JEIAE2Compact.MODID, "network_status", NetworkStatusRecipe.class),
        Collections.singletonList(new NetworkStatusRecipe(null)) // 临时空配方
    );
    
    runtime.getRecipeManager().addRecipeCategory(new NetworkStatusCategory(runtime.getGuiHelper()));
}

    @Override
    public void onRuntimeUnavailable() {
        jeiRuntime = null;
    }

    // 静态访问方法，让其他类可以获取JEI运行时
    public static IJeiRuntime getJeiRuntime() {
        return jeiRuntime;
    }
}
