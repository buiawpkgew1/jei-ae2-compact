package com.yshs.jeiae2compact.client;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;
import com.yshs.jeiae2compact.util.AE2ItemUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import java.util.ArrayList;
import java.util.List;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IGrid;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.StorageChannels;
import net.minecraft.world.entity.player.Player;
import appeng.api.stacks.KeyCounter;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.tryParse(JEIAE2Compact.MODID + ":jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CellCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new NetworkCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        List<CellRecipe> recipes = new ArrayList<>();
        
        // 获取所有存储单元
        for (int x = -16; x < 16; x++) {
            for (int y = -16; y < 16; y++) {
                for (int z = -16; z < 16; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof appeng.blockentity.storage.DriveBlockEntity drive) {
                        for (int i = 0; i < 10; i++) { // 假设最多10个存储单元
                            ItemStack cell = drive.getInternalInventory().getStackInSlot(i);
                            if (AE2ItemUtil.isStorageCell(cell)) {
                                List<ItemStack> items = AE2ItemUtil.getCellItems(cell);
                                if (!items.isEmpty()) {
                                    recipes.add(new CellRecipe(cell, items));
                                }
                            }
                        }
                    }
                }
            }
        }

        registration.addRecipes(CellCategory.TYPE, recipes);

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        // 获取玩家所在的 AE2 网络
        IGridNode node = appeng.api.util.AECableHelpers.getNodeFromEntity(player);
        if (node == null) return;

        IGrid grid = node.getGrid();
        if (grid == null) return;

        // 获取网络中的物品
        IMEMonitor<AEItemKey> monitor = grid.getStorageService().getInventory(StorageChannels.items());
        if (monitor == null) return;

        // 获取所有物品
        KeyCounter<AEItemKey> items = new KeyCounter<>();
        monitor.getAvailableStacks(items);

        // 转换为 ItemStack 列表
        List<ItemStack> networkItems = new ArrayList<>();
        for (AEItemKey key : items.keySet()) {
            networkItems.add(key.toStack());
        }

        // 注册到 JEI
        registration.addRecipes(NetworkCategory.TYPE, networkItems);
    }
} 