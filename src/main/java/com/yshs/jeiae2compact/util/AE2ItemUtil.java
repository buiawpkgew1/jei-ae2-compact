package com.yshs.jeiae2compact.util;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.ICellHandler;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class AE2ItemUtil {
    
    /**
     * 检查物品是否是 AE2 存储单元
     */
    public static boolean isStorageCell(ItemStack stack) {
        return StorageCells.getCellInventory(stack, null) != null;
    }
    
    /**
     * 获取存储单元中的物品列表
     */
    public static List<ItemStack> getCellItems(ItemStack cell) {
        List<ItemStack> items = new ArrayList<>();
        var inventory = StorageCells.getCellInventory(cell, null);
        if (inventory != null) {
            inventory.getAvailableStacks().forEach(key -> {
                if (key.getKey() instanceof AEItemKey itemKey) {
                    items.add(itemKey.toStack());
                }
            });
        }
        return items;
    }
    
    /**
     * 检查物品是否是 AE2 接口
     */
    public static boolean isInterface(ItemStack stack) {
        return stack.getItem().getDescriptionId().contains("interface");
    }
    
    /**
     * 检查物品是否是 AE2 终端
     */
    public static boolean isTerminal(ItemStack stack) {
        return stack.getItem().getDescriptionId().contains("terminal");
    }
} 