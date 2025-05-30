package com.yshs.jeiae2compact;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new Builder();
    public static final ForgeConfigSpec COMMON_SPEC;

    // 基本设置
    public static final ForgeConfigSpec.BooleanValue ENABLE_JEI_INTEGRATION;

    static {
        BUILDER.push("基本设置");
        ENABLE_JEI_INTEGRATION = BUILDER
                .comment("是否启用JEI集成")
                .define("enableJeiIntegration", true);
        BUILDER.pop();

        COMMON_SPEC = BUILDER.build();
    }
} 