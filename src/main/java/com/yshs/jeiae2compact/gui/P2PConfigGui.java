package com.yshs.jeiae2compact.gui;

import appeng.api.networking.pathing.IPathingService;
import appeng.api.parts.IPartHost;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;

public class P2PConfigGui implements IRecipeCategory<Object> {
    @Override
    public Component getTitle() {
        return Component.literal("AE2 P2P Config");
    }

    @Override
    public IDrawable getBackground() {
        // 返回背景绘制对象
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Object recipe, IFocusGroup focuses) {
        // 实现P2P配置界面
        IPartHost partHost = partHost
        // 显示P2P通道列表
        // 添加配置按钮
    }
}