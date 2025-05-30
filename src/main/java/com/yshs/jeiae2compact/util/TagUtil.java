package com.yshs.jeiae2compact.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.*;

public class TagUtil {
    private static final String TAG_KEY = "jeiae2compact_tags";
    private static final Map<String, Set<String>> itemTags = new HashMap<>();

    /**
     * 给物品添加标签
     * @param item 物品
     * @param tag 标签
     */
    public static void addTag(ItemStack item, String tag) {
        String itemId = getItemId(item);
        itemTags.computeIfAbsent(itemId, k -> new HashSet<>()).add(tag);
        saveTagsToNBT(item);
    }

    /**
     * 移除物品的标签
     * @param item 物品
     * @param tag 标签
     */
    public static void removeTag(ItemStack item, String tag) {
        String itemId = getItemId(item);
        Set<String> tags = itemTags.get(itemId);
        if (tags != null) {
            tags.remove(tag);
            if (tags.isEmpty()) {
                itemTags.remove(itemId);
            }
            saveTagsToNBT(item);
        }
    }

    /**
     * 获取物品的所有标签
     * @param item 物品
     * @return 标签集合
     */
    public static Set<String> getTags(ItemStack item) {
        String itemId = getItemId(item);
        return itemTags.getOrDefault(itemId, new HashSet<>());
    }

    /**
     * 根据标签查找物品
     * @param tag 标签
     * @param items 物品列表
     * @return 匹配的物品列表
     */
    public static List<ItemStack> findItemsByTag(String tag, List<ItemStack> items) {
        List<ItemStack> results = new ArrayList<>();
        for (ItemStack item : items) {
            if (getTags(item).contains(tag)) {
                results.add(item);
            }
        }
        return results;
    }

    /**
     * 获取所有已使用的标签
     * @return 标签集合
     */
    public static Set<String> getAllTags() {
        Set<String> allTags = new HashSet<>();
        for (Set<String> tags : itemTags.values()) {
            allTags.addAll(tags);
        }
        return allTags;
    }

    private static String getItemId(ItemStack item) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item.getItem());
        return key.toString() + ":" + item.getDamageValue();
    }

    private static void saveTagsToNBT(ItemStack item) {
        CompoundTag nbt = item.getOrCreateTag();
        CompoundTag tagCompound = new CompoundTag();
        Set<String> tags = getTags(item);
        List<String> tagList = new ArrayList<>(tags);
        tagCompound.put("tags", new net.minecraft.nbt.ListTag());
        for (String tag : tagList) {
            tagCompound.getList("tags", 8).add(net.minecraft.nbt.StringTag.valueOf(tag));
        }
        nbt.put(TAG_KEY, tagCompound);
    }

    private static void loadTagsFromNBT(ItemStack item) {
        CompoundTag nbt = item.getTag();
        if (nbt != null && nbt.contains(TAG_KEY)) {
            CompoundTag tagCompound = nbt.getCompound(TAG_KEY);
            if (tagCompound.contains("tags", 9)) { // 9 是 ListTag 的类型
                net.minecraft.nbt.ListTag tagList = tagCompound.getList("tags", 8); // 8 是 String 的类型
                Set<String> tags = new HashSet<>();
                for (int i = 0; i < tagList.size(); i++) {
                    tags.add(tagList.getString(i));
                }
                String itemId = getItemId(item);
                itemTags.put(itemId, tags);
            }
        }
    }
} 