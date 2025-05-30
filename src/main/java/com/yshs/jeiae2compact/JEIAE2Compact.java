package com.yshs.jeiae2compact;

import com.yshs.jeiae2compact.config.ModConfigManager;
import com.yshs.jeiae2compact.command.TagCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        TagCommand.register(event.getDispatcher());
    }
}
