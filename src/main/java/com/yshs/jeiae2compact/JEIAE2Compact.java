package com.yshs.jeiae2compact;

import com.yshs.jeiae2compact.config.ModConfigManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

/**
 * 主类
 */
@Mod(JEIAE2Compact.MODID)
public class JEIAE2Compact {
    /**
     * MOD ID
     */
    public static final String MODID = "jeiae2compact";

    /**
     * 构造函数
     */
    public JEIAE2Compact() {
        // 注册到MinecraftForge事件总线
        MinecraftForge.EVENT_BUS.register(this);
        
        // 注册配置
        ModConfigManager.register(MinecraftForge.EVENT_BUS);
    }
}
