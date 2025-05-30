package com.yshs.jeiae2compact.mixin;

import appeng.api.stacks.AEItemKey;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.me.common.MEStorageScreen;
import appeng.client.gui.me.common.Repo;
import appeng.client.gui.style.ScreenStyle;
import appeng.helpers.InventoryAction;
import appeng.menu.me.common.GridInventoryEntry;
import appeng.menu.me.common.MEStorageMenu;
import com.yshs.jeiae2compact.client.JEIPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * mixin MEStorageScreen
 */
@Mixin(value = MEStorageScreen.class)
public abstract class MEStorageScreenMixin<T extends MEStorageMenu> extends AEBaseScreen<T> {

    @Final
    @Shadow
    protected Repo repo;

    @Shadow
    protected abstract void setSearchString(String search);

    public MEStorageScreenMixin(T menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
    }

    /**
     * 注入到mouseClicked方法，处理中键点击物品
     */
    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        // 检查是否是中键点击
        if (Minecraft.getInstance().options.keyPickItem.matchesMouse(button)) {
            // 获取JEI的运行时
            IJeiRuntime jeiRuntime = JEIPlugin.getJeiRuntime();
            if (jeiRuntime == null) return;

            // 得到书签覆盖层下面的物品
            ItemStack itemStack = jeiRuntime.getBookmarkOverlay().getItemStackUnderMouse();
            if (itemStack == null) {
                return;
            }

            // 得到目标物品的AEKey
            AEItemKey targetKey = AEItemKey.of(itemStack);
            // 遍历AE终端中的所有条目
            repo.getAllEntries().stream()
                    // 过滤掉无法自动合成的条目
                    .filter(GridInventoryEntry::isCraftable)
                    // 过滤掉空值
                    .filter(entry -> entry.getWhat() != null)
                    // 找到目标条目
                    .filter(entry -> entry.getWhat().equals(targetKey))
                    // 打开自动合成菜单
                    .forEach(entry -> {
                        long serial = entry.getSerial();
                        menu.handleInteraction(serial, InventoryAction.AUTO_CRAFT);
                    });
        }
        // 检查是否是Shift+右键点击
        else if (button == 1 && Screen.hasShiftDown()) {
            // 获取鼠标下的物品
            GridInventoryEntry entry = getEntryAtPosition(mouseX, mouseY);
            if (entry != null && entry.isCraftable()) {
                // 自动补充到64个
                long serial = entry.getSerial();
                menu.handleInteraction(serial, InventoryAction.AUTO_CRAFT);
                cir.setReturnValue(true);
            }
        }
    }

    /**
     * 注入到keyPressed方法，处理快捷键
     */
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        // 当按下Ctrl+F时，自动聚焦到搜索框
        if (keyCode == GLFW.GLFW_KEY_F && (modifiers & GLFW.GLFW_MOD_CONTROL) != 0) {
            setSearchString("");
            cir.setReturnValue(true);
        }
    }

    /**
     * 获取指定位置的物品条目
     */
    private GridInventoryEntry getEntryAtPosition(double mouseX, double mouseY) {
        for (GridInventoryEntry entry : repo.getAllEntries()) {
            if (isMouseOver(mouseX, mouseY, entry.getX(), entry.getY(), 16, 16)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * 检查鼠标是否在指定区域内
     */
    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
} 