package com.yshs.jeiae2compact.mixin;

import appeng.api.stacks.AEItemKey;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.me.common.MEStorageScreen;
import appeng.client.gui.me.common.Repo;
import appeng.helpers.InventoryAction;
import appeng.menu.me.common.GridInventoryEntry;
import appeng.menu.me.common.MEStorageMenu;
import com.yshs.jeiae2compact.jei.JEIAE2CompactPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Mixin 注入到 MEStorageScreen，实现中键点击物品时触发自动合成功能
 *
 * @param <T> 菜单类型
 */
@Mixin(value = MEStorageScreen.class)
public abstract class MEStorageScreenMixin<T extends MEStorageMenu> extends AEBaseScreen<T> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Final
    @Shadow
    protected Repo repo;

    public MEStorageScreenMixin(T menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
    }

    /**
     * 注入到 mouseClicked 方法，处理中键点击事件
     */
    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        try {
            if (!isMiddleClick(button)) return;

            IJeiRuntime jeiRuntime = JEIAE2CompactPlugin.getJeiRuntime();
            ItemStack itemStack = getItemUnderCursor(jeiRuntime);

            if (itemStack == null || itemStack.isEmpty()) {
                showNoItemToast();
                return;
            }

            AEItemKey targetKey = AEItemKey.of(itemStack);
            findAndHandleCraftableEntry(targetKey);
        } catch (Exception e) {
            LOGGER.error("中键点击处理过程中发生异常", e);
        }
    }

    /**
     * 判断是否为中键点击
     */
    private boolean isMiddleClick(int button) {
        return Minecraft.getInstance().options.keyPickItem.matchesMouse(button);
    }

    /**
     * 获取鼠标下的物品
     */
    private ItemStack getItemUnderCursor(IJeiRuntime jeiRuntime) {
        return jeiRuntime.getBookmarkOverlay().getItemStackUnderMouse();
    }

    /**
     * 查找可合成条目并触发合成操作
     */
    private void findAndHandleCraftableEntry(AEItemKey targetKey) {
        for (GridInventoryEntry entry : repo.getAllEntries()) {
            if (!entry.isCraftable()) continue;
            if (entry.getWhat() == null) continue;
            if (entry.getWhat().equals(targetKey)) {
                menu.handleInteraction(entry.getSerial(), InventoryAction.AUTO_CRAFT);
                LOGGER.debug("成功触发自动合成功能: {}", targetKey);
                return;
            }
        }
        showNoCraftableToast();
        LOGGER.warn("未找到可自动合成的条目: {}", targetKey);
    }

    /**
     * 显示无物品提示
     */
    private void showNoItemToast() {
        JEIAE2CompactPlugin.getJeiRuntime().getJeiHelpers().getPlatformUtils()
                .showToast(Component.literal("没有检测到物品"));
    }

    /**
     * 显示无法合成提示
     */
    private void showNoCraftableToast() {
        JEIAE2CompactPlugin.getJeiRuntime().getJeiHelpers().getPlatformUtils()
                .showToast(Component.literal("该物品无法自动合成"));
    }
}