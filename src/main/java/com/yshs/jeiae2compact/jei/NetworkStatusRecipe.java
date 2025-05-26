package com.yshs.jeiae2compact.jei;

import appeng.api.networking.energy.IEnergyService;
import appeng.api.networking.IGrid;
import appeng.api.networking.storage.IStorageService;

public class NetworkStatusRecipe {
    private final IGrid grid;
    
    public NetworkStatusRecipe(IGrid grid) {
        this.grid = grid;
    }
    
    public IGrid getGrid() {
        return grid;
    }
    
    public IStorageService getStorageService() {
        return grid.getService(IStorageService.class);
    }
    
    public IEnergyService getEnergyService() {
        return grid.getService(IEnergyService.class);
    }
    
    // 添加其他网络状态获取方法
}