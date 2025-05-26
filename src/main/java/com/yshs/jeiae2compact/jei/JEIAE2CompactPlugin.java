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
    }

    @Override
    public void onRuntimeUnavailable() {
        jeiRuntime = null;
    }

    // 静态访问方法，让其他类可以获取JEI运行时
    public static IJeiRuntime getJeiRuntime() {
        return jeiRuntime;
    }

    @SubscribeEvent
    public void onCraftingComplete(PlayerEvent.ItemCraftedEvent event) {
        if(jeiRuntime != null && event.player instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.player;
            IRecipeManager recipeManager = jeiRuntime.getRecipeManager();
            
            // 获取当前合成配方
            Optional<IRecipe<?>> recipe = player.containerMenu.getRecipeBook().getRecipeFor(event.craftMatrix, player.level);
            
            if(recipe.isPresent()) {
                // 自动填充剩余材料
                fillRemainingIngredients(player, recipe.get());
            }
        }
    }

    private void fillRemainingIngredients(ServerPlayer player, IRecipe<?> recipe) {
        // 实现自动填充逻辑
        // ...
    }
}
