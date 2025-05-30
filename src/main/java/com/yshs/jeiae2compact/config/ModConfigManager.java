package com.yshs.jeiae2compact.config;

import com.yshs.jeiae2compact.Config;
import com.yshs.jeiae2compact.JEIAE2Compact;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;

public class ModConfigManager {
    private static final String CONFIG_FILE = "jeiae2compact-common.toml";

    public static void register(IEventBus modEventBus) {
        // 注册配置
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, CONFIG_FILE);
    }
} 