package com.yshs.jeiae2compact.util;

import net.minecraft.world.item.ItemStack;
import java.util.*;
import java.util.stream.Collectors;

public class ItemGrouper {
    /**
     * 按物品类型分组
     * @param items 物品列表
     * @return 分组后的物品映射
     */
    public static Map<String, List<ItemStack>> groupByType(List<ItemStack> items) {
        return items.stream()
                .collect(Collectors.groupingBy(item -> 
                    item.getItem().getRegistryName().toString()));
    }

    /**
     * 按物品名称分组
     * @param items 物品列表
     * @return 分组后的物品映射
     */
    public static Map<String, List<ItemStack>> groupByName(List<ItemStack> items) {
        return items.stream()
                .collect(Collectors.groupingBy(item -> 
                    item.getDisplayName().getString()));
    }

    /**
     * 按物品数量分组
     * @param items 物品列表
     * @return 分组后的物品映射
     */
    public static Map<Integer, List<ItemStack>> groupByCount(List<ItemStack> items) {
        return items.stream()
                .collect(Collectors.groupingBy(ItemStack::getCount));
    }

    /**
     * 按物品标签分组
     * @param items 物品列表
     * @return 分组后的物品映射
     */
    public static Map<String, List<ItemStack>> groupByTags(List<ItemStack> items) {
        Map<String, List<ItemStack>> result = new HashMap<>();
        
        for (ItemStack item : items) {
            Set<String> tags = TagUtil.getTags(item);
            for (String tag : tags) {
                result.computeIfAbsent(tag, k -> new ArrayList<>()).add(item);
            }
        }
        
        return result;
    }

    /**
     * 按相似度分组
     * @param items 物品列表
     * @param similarityThreshold 相似度阈值
     * @return 分组后的物品列表
     */
    public static List<List<ItemStack>> groupBySimilarity(List<ItemStack> items, double similarityThreshold) {
        List<List<ItemStack>> groups = new ArrayList<>();
        Set<ItemStack> processed = new HashSet<>();

        for (ItemStack item : items) {
            if (processed.contains(item)) {
                continue;
            }

            List<ItemStack> group = new ArrayList<>();
            group.add(item);
            processed.add(item);

            for (ItemStack other : items) {
                if (processed.contains(other)) {
                    continue;
                }

                if (ItemComparator.compareItems(item, other) >= similarityThreshold) {
                    group.add(other);
                    processed.add(other);
                }
            }

            if (!group.isEmpty()) {
                groups.add(group);
            }
        }

        return groups;
    }
} 