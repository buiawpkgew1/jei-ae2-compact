package com.yshs.jeiae2compact.jei;

import com.yshs.jeiae2compact.JEIAE2Compact;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
        if(jeiRuntime != null && event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            Recipe<?> recipe = event.getRecipe();
            
            if(recipe != null) {
                fillRemainingIngredients(player, recipe);
            }
        }
    }

    private void fillRemainingIngredients(ServerPlayer player, Recipe<?> recipe) {
        // Implementation of auto-fill logic
    }
}
