package com.yshs.jeiae2compact.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import java.util.Comparator;

public class ItemComparator {
    /**
     * 比较两个物品的相似度
     * @param item1 第一个物品
     * @param item2 第二个物品
     * @return 相似度（0-1之间）
     */
    public static double compareItems(ItemStack item1, ItemStack item2) {
        if (item1.isEmpty() || item2.isEmpty()) {
            return 0.0;
        }

        double similarity = 0.0;
        
        // 比较物品类型
        if (item1.getItem() == item2.getItem()) {
            similarity += 0.4;
        }
        
        // 比较NBT数据
        CompoundTag nbt1 = item1.getTag();
        CompoundTag nbt2 = item2.getTag();
        if (nbt1 != null && nbt2 != null) {
            if (nbt1.equals(nbt2)) {
                similarity += 0.3;
            }
        } else if (nbt1 == null && nbt2 == null) {
            similarity += 0.3;
        }
        
        // 比较物品名称
        String name1 = item1.getDisplayName().getString().toLowerCase();
        String name2 = item2.getDisplayName().getString().toLowerCase();
        if (name1.equals(name2)) {
            similarity += 0.3;
        }
        
        return similarity;
    }

    /**
     * 创建一个按名称排序的比较器
     * @return 比较器
     */
    public static Comparator<ItemStack> createNameComparator() {
        return (item1, item2) -> {
            String name1 = item1.getDisplayName().getString();
            String name2 = item2.getDisplayName().getString();
            return name1.compareToIgnoreCase(name2);
        };
    }

    /**
     * 创建一个按物品类型排序的比较器
     * @return 比较器
     */
    public static Comparator<ItemStack> createTypeComparator() {
        return (item1, item2) -> {
            Item type1 = item1.getItem();
            Item type2 = item2.getItem();
            return type1.getRegistryName().toString()
                    .compareToIgnoreCase(type2.getRegistryName().toString());
        };
    }

    /**
     * 创建一个按数量排序的比较器
     * @return 比较器
     */
    public static Comparator<ItemStack> createCountComparator() {
        return (item1, item2) -> Integer.compare(item2.getCount(), item1.getCount());
    }
} 