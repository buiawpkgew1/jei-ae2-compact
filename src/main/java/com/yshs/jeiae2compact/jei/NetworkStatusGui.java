package com.yshs.jeiae2compact.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;

public class NetworkStatusGui extends GuiComponent implements IDrawable {
    
    private final int width;
    private final int height;
    
    public NetworkStatusGui(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    // 添加详细的网络状态显示
    public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        // 绘制网络基本信息
        drawString(poseStack, font, "Energy: " + energyService.getStoredPower(), x+10, y+20, 0xFFFFFF);
        // 添加存储单元统计等更多信息
    }
}