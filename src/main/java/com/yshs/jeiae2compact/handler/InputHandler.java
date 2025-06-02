package com.yshs.jeiae2compact.handler;

import com.yshs.jeiae2compact.service.CraftingService;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.world.item.ItemStack;

/**
 * 输入事件处理器，负责中键点击后的 JEI & AE2 交互逻辑。
 */
public class InputHandler {

    private final IJeiRuntime jeiRuntime;
    private final CraftingService craftingService;

    public InputHandler(IJeiRuntime jeiRuntime, CraftingService craftingService) {
        this.jeiRuntime = jeiRuntime;
        this.craftingService = craftingService;
    }

    /**
     * 处理中键点击事件。
     */
    public void handleMiddleClick() {
        ItemStack itemStack = getItemUnderCursor();
        if (itemStack == null || itemStack.isEmpty()) {
            showToast("没有检测到物品");
            return;
        }

        if (!craftingService.tryAutoCraft(itemStack)) {
            showToast("该物品无法自动合成");
        }
    }

    private ItemStack getItemUnderCursor() {
        return jeiRuntime.getBookmarkOverlay().getItemStackUnderMouse();
    }

    private void showToast(String message) {
        jeiRuntime.getJeiHelpers().getPlatformUtils().showToast(message);
    }
}