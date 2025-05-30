package com.yshs.jeiae2compact.util;

import com.yshs.jeiae2compact.Config;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class SearchUtil {
    /**
     * 执行物品搜索
     * @param searchText 搜索文本
     * @param items 物品列表
     * @return 匹配的物品列表
     */
    public static List<ItemStack> searchItems(String searchText, List<ItemStack> items) {
        if (searchText == null || searchText.isEmpty()) {
            return items;
        }

        searchText = searchText.toLowerCase();
        List<ItemStack> results = new ArrayList<>();
        Predicate<ItemStack> searchPredicate;

        if (Config.ENABLE_FUZZY_SEARCH.get()) {
            searchPredicate = item -> {
                String itemName = item.getDisplayName().getString().toLowerCase();
                return fuzzyMatch(itemName, searchText);
            };
        } else {
            searchPredicate = item -> {
                String itemName = item.getDisplayName().getString().toLowerCase();
                return itemName.contains(searchText);
            };
        }

        for (ItemStack item : items) {
            if (searchPredicate.test(item)) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * 模糊匹配算法
     * @param text 文本
     * @param pattern 模式
     * @return 是否匹配
     */
    private static boolean fuzzyMatch(String text, String pattern) {
        if (pattern.length() > text.length()) {
            return false;
        }

        int patternIndex = 0;
        for (int i = 0; i < text.length() && patternIndex < pattern.length(); i++) {
            if (text.charAt(i) == pattern.charAt(patternIndex)) {
                patternIndex++;
            }
        }

        return patternIndex == pattern.length();
    }

    /**
     * 获取物品的详细信息
     * @param item 物品
     * @return 详细信息组件
     */
    public static Component getItemDetails(ItemStack item) {
        StringBuilder details = new StringBuilder();
        details.append(item.getDisplayName().getString());
        
        if (Config.SHOW_ITEM_COUNT.get()) {
            details.append(" x").append(item.getCount());
        }

        return Component.literal(details.toString());
    }
} 