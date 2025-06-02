package com.yshs.jeiae2compact.service;

import appeng.api.stacks.AEItemKey;
import appeng.client.gui.me.common.Repo;
import appeng.helpers.InventoryAction;
import appeng.menu.me.common.GridInventoryEntry;
import appeng.menu.me.common.MEStorageMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * 合成服务类，负责查找并触发 AE2 自动合成逻辑。
 */
public class CraftingService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Repo repo;
    private final MEStorageMenu menu;

    public CraftingService(Repo repo, MEStorageMenu menu) {
        this.repo = repo;
        this.menu = menu;
    }

    /**
     * 根据物品堆栈尝试触发自动合成。
     *
     * @param itemStack 点击的物品堆栈
     * @return 是否成功找到并触发合成
     */
    public boolean tryAutoCraft(ItemStack itemStack) {
        if (itemStack == null || itemStack.isEmpty()) {
            return false;
        }

        AEItemKey targetKey = AEItemKey.of(itemStack);
        return findAndHandleCraftableEntry(targetKey);
    }

    /**
     * 查找匹配的可合成条目并触发合成操作。
     */
    private boolean findAndHandleCraftableEntry(AEItemKey targetKey) {
        Optional<GridInventoryEntry> entryOpt = repo.getAllEntries().stream()
                .filter(GridInventoryEntry::isCraftable)
                .filter(entry -> entry.getWhat() != null)
                .filter(entry -> entry.getWhat().equals(targetKey))
                .findFirst();

        if (entryOpt.isPresent()) {
            menu.handleInteraction(entryOpt.get().getSerial(), InventoryAction.AUTO_CRAFT);
            LOGGER.debug("成功触发自动合成功能: {}", targetKey);
            return true;
        } else {
            LOGGER.warn("未找到可自动合成的条目: {}", targetKey);
            return false;
        }
    }
}