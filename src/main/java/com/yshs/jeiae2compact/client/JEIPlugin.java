package com.yshs.jeiae2compact.client;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IGrid;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.networking.IGridNodeHost;
import appeng.api.networking.IGridNodeable;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.tryParse(JEIAE2Compact.MODID + ":jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new NetworkCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        // 获取玩家所在的 AE2 网络
        IGridNode node = null;
        if (player instanceof IGridNodeable) {
            node = ((IGridNodeable) player).getMainNode();
        }
        if (node == null) return;

        IGrid grid = node.getGrid();
        if (grid == null) return;

        // 获取网络中的物品
        var storage = grid.getStorageService();
        if (storage == null) return;

        // 获取所有物品
        KeyCounter items = new KeyCounter();
        storage.getAvailableStacks(items);

        // 转换为 ItemStack 列表
        List<ItemStack> networkItems = new ArrayList<>();
        for (AEKey key : items.keySet()) {
            if (key instanceof AEItemKey itemKey) {
                networkItems.add(itemKey.toStack());
            }
        }

        // 注册到 JEI
        registration.addRecipes(NetworkCategory.TYPE, networkItems);
    }
} 