package com.yshs.jeiae2compact.client;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import com.yshs.jeiae2compact.JEIAE2Compact;
import com.yshs.jeiae2compact.util.AE2ItemUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.tryParse(JEIAE2Compact.MODID + ":jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CellCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        List<CellRecipe> recipes = new ArrayList<>();
        
        // 获取所有存储单元
        level.getEntities().forEach(entity -> {
            if (entity instanceof appeng.blockentity.networking.StorageBusBlockEntity storageBus) {
                ItemStack cell = storageBus.getCell();
                if (AE2ItemUtil.isStorageCell(cell)) {
                    List<ItemStack> items = AE2ItemUtil.getCellItems(cell);
                    if (!items.isEmpty()) {
                        recipes.add(new CellRecipe(cell, items));
                    }
                }
            }
        });

        registration.addRecipes(CellCategory.TYPE, recipes);
    }
} 