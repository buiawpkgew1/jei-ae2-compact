package com.yshs.jeiae2compact.mixin;

import appeng.api.stacks.AEItemKey;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.me.common.MEStorageScreen;
import appeng.client.gui.me.common.Repo;
import appeng.client.gui.style.ScreenStyle;
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

/**
 * mixin MEStorageScreen
 *
 * @param <T> idk
 */
@Mixin(value = MEStorageScreen.class)
public abstract class MEStorageScreenMixin<T extends MEStorageMenu> extends AEBaseScreen<T> {

    @Final
    @Shadow
    protected Repo repo;

    @SuppressWarnings("MissingJavadoc")
    public MEStorageScreenMixin(T menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
    }

    /**
     * 注入到mouseClicked方法，处理中键点击物品
     */
    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        // 获取Minecraft实例和JEI运行时
        Minecraft minecraft = Minecraft.getInstance();
        IJeiRuntime jeiRuntime = JEIAE2CompactPlugin.getJeiRuntime();

        // 检查是否是中键点击
        if (!minecraft.options.keyPickItem.matchesMouse(button)) {
            return;
        }

        // 获取书签覆盖层下的物品
        ItemStack itemStack = jeiRuntime != null ? jeiRuntime.getBookmarkOverlay().getItemStackUnderMouse() : null;
        if (itemStack == null || jeiRuntime == null) {
            return;
        }

        // 得到目标物品的AEKey
        AEItemKey targetKey = AEItemKey.of(itemStack);
        if (targetKey == null) {
            return;
        }

        // 遍历AE终端中的所有条目并打开自动合成菜单
        repo.getAllEntries().stream()
                .filter(GridInventoryEntry::isCraftable)
                .filter(entry -> entry.getWhat() != null)
                .filter(entry -> entry.getWhat().equals(targetKey))
                .forEach(entry -> {
                    long serial = entry.getSerial();
                    menu.handleInteraction(serial, InventoryAction.AUTO_CRAFT);

                    // 合成完成后，再模拟一次拾取/放置动作，尝试填充到合成栏
                    menu.handleInteraction(serial, InventoryAction.PICKUP_OR_SET_DOWN);
                });
    }
}
