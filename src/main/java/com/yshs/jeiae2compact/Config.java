package com.yshs.jeiae2compact;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new Builder();
    public static final ForgeConfigSpec COMMON_SPEC;

    // 基本设置
    public static final ForgeConfigSpec.BooleanValue ENABLE_JEI_INTEGRATION;
    public static final ForgeConfigSpec.BooleanValue ENABLE_MIDDLE_CLICK_AUTO_CRAFT;

    static {
        BUILDER.push("基本设置");
        ENABLE_JEI_INTEGRATION = BUILDER
                .comment("是否启用JEI集成")
                .define("enableJeiIntegration", true);

        ENABLE_MIDDLE_CLICK_AUTO_CRAFT = BUILDER
                .comment("是否启用中键点击物品自动合成功能")
                .define("enableMiddleClickAutoCraft", true);

        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
}