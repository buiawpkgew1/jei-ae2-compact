package com.yshs.jeiae2compact.p2p;

public class P2PPresetManager {
    public static void savePreset(String name, P2PConfig config) {
        // 保存P2P配置预设
    }
    
    public static P2PConfig loadPreset(String name) {
        // 加载P2P配置预设
        return new P2PConfig();
    }
    
    // 添加预设文件存在性检查
    public static boolean presetExists(String name) {
        return new File(getPresetPath(name)).exists();
    }
}