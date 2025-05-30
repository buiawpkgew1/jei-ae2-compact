package com.yshs.jeiae2compact.config;

import com.yshs.jeiae2compact.Config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModConfigManager {
    private static final String CONFIG_FILE = "jeiae2compact-common.toml";

    public static void register(IEventBus modEventBus) {
        // 注册配置
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, CONFIG_FILE);
        
        // 注册配置事件监听器
        modEventBus.addListener(ModConfigManager::onConfigLoad);
        modEventBus.addListener(ModConfigManager::onConfigReload);
    }

    private static void onConfigLoad(final ModConfigEvent.Loading event) {
        // 配置加载时的处理
    }

    private static void onConfigReload(final ModConfigEvent.Reloading event) {
        // 配置重载时的处理
    }
} 