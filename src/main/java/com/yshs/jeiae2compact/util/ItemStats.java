package com.yshs.jeiae2compact.util;

import net.minecraft.world.item.ItemStack;
import java.util.*;
import java.util.stream.Collectors;

public class ItemStats {
    /**
     * 获取物品列表的统计信息
     * @param items 物品列表
     * @return 统计信息
     */
    public static Map<String, Object> getStats(List<ItemStack> items) {
        Map<String, Object> stats = new HashMap<>();
        
        // 总物品数量
        stats.put("totalItems", items.size());
        
        // 不同物品类型的数量
        stats.put("uniqueTypes", items.stream()
                .map(item -> item.getItem().getRegistryName().toString())
                .collect(Collectors.toSet())
                .size());
        
        // 物品总数
        stats.put("totalCount", items.stream()
                .mapToInt(ItemStack::getCount)
                .sum());
        
        // 最常用的物品
        Map<String, Integer> typeCounts = items.stream()
                .collect(Collectors.groupingBy(
                    item -> item.getItem().getRegistryName().toString(),
                    Collectors.summingInt(ItemStack::getCount)
                ));
        
        String mostCommonType = typeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("无");
        
        stats.put("mostCommonType", mostCommonType);
        stats.put("mostCommonCount", typeCounts.getOrDefault(mostCommonType, 0));
        
        // 物品标签统计
        Map<String, Integer> tagStats = new HashMap<>();
        for (ItemStack item : items) {
            Set<String> tags = TagUtil.getTags(item);
            for (String tag : tags) {
                tagStats.merge(tag, 1, Integer::sum);
            }
        }
        stats.put("tagStats", tagStats);
        
        // 收藏物品统计
        int favoriteCount = (int) items.stream()
                .filter(FavoriteUtil::isFavorite)
                .count();
        stats.put("favoriteCount", favoriteCount);
        
        return stats;
    }

    /**
     * 获取物品列表的详细统计信息
     * @param items 物品列表
     * @return 详细统计信息
     */
    public static Map<String, Map<String, Object>> getDetailedStats(List<ItemStack> items) {
        Map<String, Map<String, Object>> detailedStats = new HashMap<>();
        
        // 按类型统计
        Map<String, List<ItemStack>> typeGroups = ItemGrouper.groupByType(items);
        for (Map.Entry<String, List<ItemStack>> entry : typeGroups.entrySet()) {
            Map<String, Object> typeStats = new HashMap<>();
            List<ItemStack> groupItems = entry.getValue();
            
            typeStats.put("count", groupItems.size());
            typeStats.put("totalItems", groupItems.stream()
                    .mapToInt(ItemStack::getCount)
                    .sum());
            typeStats.put("tags", groupItems.stream()
                    .flatMap(item -> TagUtil.getTags(item).stream())
                    .collect(Collectors.toSet()));
            typeStats.put("isFavorite", groupItems.stream()
                    .anyMatch(FavoriteUtil::isFavorite));
            
            detailedStats.put(entry.getKey(), typeStats);
        }
        
        return detailedStats;
    }

    /**
     * 获取物品列表的标签统计信息
     * @param items 物品列表
     * @return 标签统计信息
     */
    public static Map<String, Set<String>> getTagStats(List<ItemStack> items) {
        Map<String, Set<String>> tagStats = new HashMap<>();
        
        for (ItemStack item : items) {
            String itemId = item.getItem().getRegistryName().toString();
            Set<String> tags = TagUtil.getTags(item);
            
            if (!tags.isEmpty()) {
                tagStats.put(itemId, tags);
            }
        }
        
        return tagStats;
    }
} 