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

        COMMON_SPEC = BUILDER.build();
    }
} 