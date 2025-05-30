package com.yshs.jeiae2compact;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * 主类
 */
@Mod(JEIAE2Compact.MODID)
public class JEIAE2Compact {
    /**
     * MOD ID
     */
    public static final String MODID = "jeiae2compact";

    @SuppressWarnings("MissingJavadoc")
    public JEIAE2Compact() {
        // 注册到MinecraftForge事件总线，确保客户端事件能够被正确处理
        MinecraftForge.EVENT_BUS.register(this);
        
        // 注册配置
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, "jeiae2compact-common.toml");
        
        // 注册配置事件监听器
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onConfigReload);
    }
    
    private void onConfigLoad(final ModConfigEvent.Loading event) {
        // 配置加载时的处理
    }
    
    private void onConfigReload(final ModConfigEvent.Reloading event) {
        // 配置重载时的处理
    }
}
