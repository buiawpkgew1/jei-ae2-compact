package com.yshs.jeiae2compact.util;

import com.yshs.jeiae2compact.Config;
import net.minecraft.world.item.ItemStack;
import java.util.*;

public class CategoryUtil {
    private static final Map<String, List<ItemStack>> customCategories = new HashMap<>();

    /**
     * 添加物品到自定义分类
     * @param category 分类名称
     * @param item 物品
     */
    public static void addItemToCategory(String category, ItemStack item) {
        if (!Config.ENABLE_CUSTOM_CATEGORIES.get()) {
            return;
        }

        customCategories.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
    }

    /**
     * 获取分类中的所有物品
     * @param category 分类名称
     * @return 物品列表
     */
    public static List<ItemStack> getItemsInCategory(String category) {
        return customCategories.getOrDefault(category, new ArrayList<>());
    }

    /**
     * 获取所有自定义分类
     * @return 分类名称列表
     */
    public static List<String> getCustomCategories() {
        return new ArrayList<>(customCategories.keySet());
    }

    /**
     * 对物品列表进行排序
     * @param items 物品列表
     * @return 排序后的物品列表
     */
    public static List<ItemStack> sortItems(List<ItemStack> items) {
        if (!Config.ENABLE_QUICK_SORT.get()) {
            return items;
        }

        List<ItemStack> sortedItems = new ArrayList<>(items);
        sortedItems.sort((a, b) -> {
            String nameA = a.getDisplayName().getString();
            String nameB = b.getDisplayName().getString();
            return nameA.compareToIgnoreCase(nameB);
        });

        return sortedItems;
    }

    /**
     * 清除所有自定义分类
     */
    public static void clearCustomCategories() {
        customCategories.clear();
    }
} 