package com.yshs.jeiae2compact;

import com.yshs.jeiae2compact.config.ModConfigManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;

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
    public JEIAE2Compact(IEventBus modEventBus) {
        // 注册到MinecraftForge事件总线，确保客户端事件能够被正确处理
        MinecraftForge.EVENT_BUS.register(this);
        
        // 注册配置
        ModConfigManager.register(modEventBus);
    }
}
