package com.yshs.jeiae2compact.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import java.util.*;

public class FavoriteUtil {
    private static final String FAVORITE_KEY = "jeiae2compact_favorites";
    private static final Set<String> favoriteItems = new HashSet<>();

    /**
     * 添加物品到收藏
     * @param item 物品
     */
    public static void addFavorite(ItemStack item) {
        String itemId = getItemId(item);
        favoriteItems.add(itemId);
        saveFavorites();
    }

    /**
     * 从收藏中移除物品
     * @param item 物品
     */
    public static void removeFavorite(ItemStack item) {
        String itemId = getItemId(item);
        favoriteItems.remove(itemId);
        saveFavorites();
    }

    /**
     * 检查物品是否已收藏
     * @param item 物品
     * @return 是否已收藏
     */
    public static boolean isFavorite(ItemStack item) {
        String itemId = getItemId(item);
        return favoriteItems.contains(itemId);
    }

    /**
     * 获取所有收藏的物品
     * @param items 物品列表
     * @return 收藏的物品列表
     */
    public static List<ItemStack> getFavorites(List<ItemStack> items) {
        List<ItemStack> favorites = new ArrayList<>();
        for (ItemStack item : items) {
            if (isFavorite(item)) {
                favorites.add(item);
            }
        }
        return favorites;
    }

    /**
     * 获取收藏物品的数量
     * @return 收藏物品数量
     */
    public static int getFavoriteCount() {
        return favoriteItems.size();
    }

    private static String getItemId(ItemStack item) {
        return item.getItem().getRegistryName().toString() + ":" + item.getDamageValue();
    }

    private static void saveFavorites() {
        // 这里可以添加保存到配置文件或数据库的逻辑
    }

    /**
     * 加载收藏列表
     */
    public static void loadFavorites() {
        // 这里可以添加从配置文件或数据库加载的逻辑
    }
} 