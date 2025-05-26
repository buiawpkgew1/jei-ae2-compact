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
    public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        // 绘制背景
        this.blit(poseStack, xOffset, yOffset, 0, 0, width, height);
        
        // 绘制网络状态信息
        font.draw(poseStack, "AE2网络状态", xOffset + 10, yOffset + 10, 0x404040);
        font.draw(poseStack, "物品数量: " + status.getItemCount(), xOffset + 10, yOffset + 25, 0x404040);
        font.draw(poseStack, "能量: " + status.getEnergy() + "/" + status.getMaxEnergy(), xOffset + 10, yOffset + 40, 0x404040);
        font.draw(poseStack, "节点数量: " + status.getNodeCount(), xOffset + 10, yOffset + 55, 0x404040);
    }
}