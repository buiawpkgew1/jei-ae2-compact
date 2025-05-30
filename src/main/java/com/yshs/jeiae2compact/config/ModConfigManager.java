package com.yshs.jeiae2compact.config;

import com.yshs.jeiae2compact.Config;
import com.yshs.jeiae2compact.JEIAE2Compact;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;

@Mod.EventBusSubscriber(modid = JEIAE2Compact.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigManager {
    private static final String CONFIG_FILE = "jeiae2compact-common.toml";

    public static void register(IEventBus modEventBus) {
        // 注册配置
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, CONFIG_FILE);
    }

    @SubscribeEvent
    public static void onConfigLoad(final ModConfigEvent.Loading event) {
        // 配置加载时的处理
    }

    @SubscribeEvent
    public static void onConfigReload(final ModConfigEvent.Reloading event) {
        // 配置重载时的处理
    }
} 