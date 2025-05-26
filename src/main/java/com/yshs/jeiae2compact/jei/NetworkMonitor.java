package com.yshs.jeiae2compact.jei;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IEnergyService;
import appeng.api.networking.storage.IStorageService;
import net.minecraft.world.entity.player.Player;

public class NetworkMonitor {
    
    public static NetworkStatusRecipe getNetworkStatus(Player player) {
        // 获取玩家当前所在的AE2网络
        IGridNode node = ...; // 需要实现获取玩家所在网格节点的逻辑
        if (node != null) {
            IGrid grid = node.getGrid();
            return new NetworkStatusRecipe(grid);
        }
        return null;
    }
    
    // 添加其他网络监控方法
}

// 添加获取玩家所在网格节点的完整实现
public static IGridNode getPlayerGridNode(Player player) {
    Level level = player.level();
    BlockPos pos = player.blockPosition();
    IGridNode node = GridHelper.getNode(level, pos);
    return node != null ? node : GridHelper.getNode(level, pos.above());
}