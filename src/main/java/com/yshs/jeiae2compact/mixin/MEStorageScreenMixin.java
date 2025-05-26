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
     * 检查并补充缺少的材料
     */
    private void checkAndRequestMissingMaterials(ItemStack itemStack) {
        // 获取JEI运行时
        IJeiRuntime jeiRuntime = JEIAE2CompactPlugin.getJeiRuntime();
        // 获取当前查看的配方
        mezz.jei.api.recipe.IFocus<?> focus = jeiRuntime.getRecipesGui().getFocus();
        
        if (focus != null && focus.getMode() == mezz.jei.api.recipe.RecipeIngredientRole.INPUT) {
            // 获取配方输入物品
            Object ingredient = focus.getTypedValue().getIngredient();
            if (ingredient instanceof ItemStack) {
                // 检查网络中的物品数量
                AEItemKey key = AEItemKey.of((ItemStack) ingredient);
                long available = menu.getClientRepo().getByKey(key).getStoredAmount();
                long required = itemStack.getCount();
                
                // 如果数量不足，触发自动合成
                if (available < required) {
                    repo.getAllEntries().stream()
                        .filter(entry -> entry.getWhat().equals(key))
                        .filter(GridInventoryEntry::isCraftable)
                        .findFirst()
                        .ifPresent(entry -> {
                            long missing = required - available;
                            // 设置合成数量为缺少的数量
                            menu.setAutoCraftAmount(missing);
                            // 触发自动合成
                            menu.handleInteraction(entry.getSerial(), InventoryAction.AUTO_CRAFT);
                        });
                }
            }
        }
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().options.keyPickItem.matchesMouse(button)) {
            IJeiRuntime jeiRuntime = JEIAE2CompactPlugin.getJeiRuntime();
            ItemStack itemStack = jeiRuntime.getBookmarkOverlay().getItemStackUnderMouse();
            if (itemStack != null) {
                // 检查并补充缺少的材料
                checkAndRequestMissingMaterials(itemStack);
                
                // 原有自动合成逻辑
                AEItemKey targetKey = AEItemKey.of(itemStack);
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
}
