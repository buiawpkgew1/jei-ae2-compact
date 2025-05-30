package com.yshs.jeiae2compact;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new Builder();
    public static final ForgeConfigSpec COMMON_SPEC;

    // 搜索相关配置
    public static final ForgeConfigSpec.BooleanValue ENABLE_ADVANCED_SEARCH;
    public static final ForgeConfigSpec.IntValue SEARCH_DELAY;
    public static final ForgeConfigSpec.BooleanValue ENABLE_FUZZY_SEARCH;

    // 分类相关配置
    public static final ForgeConfigSpec.BooleanValue ENABLE_CUSTOM_CATEGORIES;
    public static final ForgeConfigSpec.BooleanValue SHOW_ITEM_COUNT;
    public static final ForgeConfigSpec.BooleanValue ENABLE_QUICK_SORT;

    // 标签相关配置
    public static final ForgeConfigSpec.BooleanValue ENABLE_TAGS;
    public static final ForgeConfigSpec.IntValue MAX_TAGS_PER_ITEM;
    public static final ForgeConfigSpec.BooleanValue SHOW_TAGS_IN_TOOLTIP;

    // 收藏相关配置
    public static final ForgeConfigSpec.BooleanValue ENABLE_FAVORITES;
    public static final ForgeConfigSpec.IntValue MAX_FAVORITES;
    public static final ForgeConfigSpec.BooleanValue SHOW_FAVORITE_ICON;

    static {
        BUILDER.push("搜索设置");
        ENABLE_ADVANCED_SEARCH = BUILDER
                .comment("是否启用高级搜索功能")
                .define("enableAdvancedSearch", true);
        SEARCH_DELAY = BUILDER
                .comment("搜索延迟（毫秒）")
                .defineInRange("searchDelay", 200, 0, 1000);
        ENABLE_FUZZY_SEARCH = BUILDER
                .comment("是否启用模糊搜索")
                .define("enableFuzzySearch", true);
        BUILDER.pop();

        BUILDER.push("分类设置");
        ENABLE_CUSTOM_CATEGORIES = BUILDER
                .comment("是否启用自定义分类")
                .define("enableCustomCategories", true);
        SHOW_ITEM_COUNT = BUILDER
                .comment("是否显示物品数量")
                .define("showItemCount", true);
        ENABLE_QUICK_SORT = BUILDER
                .comment("是否启用快速排序")
                .define("enableQuickSort", true);
        BUILDER.pop();

        BUILDER.push("标签设置");
        ENABLE_TAGS = BUILDER
                .comment("是否启用物品标签功能")
                .define("enableTags", true);
        MAX_TAGS_PER_ITEM = BUILDER
                .comment("每个物品最多可以添加的标签数量")
                .defineInRange("maxTagsPerItem", 5, 1, 20);
        SHOW_TAGS_IN_TOOLTIP = BUILDER
                .comment("是否在物品提示中显示标签")
                .define("showTagsInTooltip", true);
        BUILDER.pop();

        BUILDER.push("收藏设置");
        ENABLE_FAVORITES = BUILDER
                .comment("是否启用收藏功能")
                .define("enableFavorites", true);
        MAX_FAVORITES = BUILDER
                .comment("最多可以收藏的物品数量")
                .defineInRange("maxFavorites", 50, 1, 200);
        SHOW_FAVORITE_ICON = BUILDER
                .comment("是否显示收藏图标")
                .define("showFavoriteIcon", true);
        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
} 