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
        // 绘制网络状态UI元素
        drawString(poseStack, Minecraft.getInstance().font, 
            Component.literal("AE2 Network Status"), 
            xOffset + 5, yOffset + 5, 0xFFFFFF);
        
        // 添加其他网络状态信息的绘制
    }
}