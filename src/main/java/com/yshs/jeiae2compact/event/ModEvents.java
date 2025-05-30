package com.yshs.jeiae2compact.event;

import com.yshs.jeiae2compact.util.FavoriteUtil;
import com.yshs.jeiae2compact.util.TagUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.yshs.jeiae2compact.JEIAE2Compact;

@Mod.EventBusSubscriber(modid = JEIAE2Compact.MODID)
public class ModEvents {
    
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();
        
        // 显示标签
        if (!TagUtil.getTags(item).isEmpty()) {
            event.getToolTip().add(Component.literal("标签: " + String.join(", ", TagUtil.getTags(item))));
        }
        
        // 显示收藏状态
        if (FavoriteUtil.isFavorite(item)) {
            event.getToolTip().add(Component.literal("★ 已收藏"));
        }
    }
    
    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide) {
            ItemStack item = event.getItemStack();
            
            // 右键点击时切换收藏状态
            if (event.getEntity().isShiftKeyDown()) {
                if (FavoriteUtil.isFavorite(item)) {
                    FavoriteUtil.removeFavorite(item);
                    event.getEntity().displayClientMessage(Component.literal("已取消收藏"), true);
                } else {
                    FavoriteUtil.addFavorite(item);
                    event.getEntity().displayClientMessage(Component.literal("已添加到收藏"), true);
                }
                event.setCanceled(true);
            }
        }
    }
} 