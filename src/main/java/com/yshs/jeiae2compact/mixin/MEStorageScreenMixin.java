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
        if (Minecraft.getInstance().options.keyPickItem.matchesMouse(button)) {
            IJeiRuntime jeiRuntime = JEIAE2CompactPlugin.getJeiRuntime();
            ItemStack itemStack = jeiRuntime.getBookmarkOverlay().getItemStackUnderMouse();
            if (itemStack == null) {
                return;
            }
            
            // 检查材料是否足够
            AEItemKey targetKey = AEItemKey.of(itemStack);
            long missingAmount = itemStack.getCount() - menu.getClientRepo().getStack(targetKey)
                    .map(GridInventoryEntry::getStoredAmount)
                    .orElse(0L);
                    
            // 如果材料不足，自动请求补充
            if (missingAmount > 0) {
                menu.getPlayerInventory().items.stream()
                    .filter(stack -> !stack.isEmpty())
                    .filter(stack -> AEItemKey.of(stack).equals(targetKey))
                    .findFirst()
                    .ifPresent(stack -> {
                        // 请求补充缺少的数量
                        menu.sendClientAction("fill_item", (int)missingAmount);
                        Minecraft.getInstance().player.displayClientMessage(
                            Component.literal("自动请求补充 " + missingAmount + " 个 " + itemStack.getDisplayName().getString()), 
                            true);
                    });
            }
            
            // 原有自动合成逻辑
            repo.getAllEntries().stream()
                    .filter(GridInventoryEntry::isCraftable)
                    .filter(entry -> entry.getWhat() != null)
                    .filter(entry -> entry.getWhat().equals(targetKey))
                    .forEach(entry -> {
                        long serial = entry.getSerial();
                        menu.handleInteraction(serial, InventoryAction.AUTO_CRAFT);
                    });
        }
    }
}
